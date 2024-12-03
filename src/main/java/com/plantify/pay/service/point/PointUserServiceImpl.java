package com.plantify.pay.service.point;

import com.plantify.pay.config.RedisLock;
import com.plantify.pay.domain.dto.point.PointUseRequest;
import com.plantify.pay.domain.dto.point.PointUseResponse;
import com.plantify.pay.global.exception.errorcode.PointErrorCode;
import com.plantify.pay.util.UserInfoProvider;
import com.plantify.pay.domain.dto.point.PointUserResponse;
import com.plantify.pay.domain.entity.Point;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.repository.PointRepository;
import io.lettuce.core.RedisClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointUserServiceImpl implements PointUserService {

    private final PointRepository pointRepository;
    private final UserInfoProvider userInfoProvider;
    private final RedisLock redisLock;

    private static final int LOCK_TIMEOUT_MS = 3000;

    @Override
    public PointUserResponse getUserPoints() {
        Long userId = userInfoProvider.getUserInfo().userId();
        Point point = pointRepository.findByUserId(userId)
                .orElseGet(() -> pointRepository.save(Point.builder()
                        .userId(userId)
                        .pointBalance(0L)
                        .accumulatedPoints(0L)
                        .redeemedPoints(0L)
                        .build()));
        return PointUserResponse.from(point);
    }

    @Override
    @Transactional
    public PointUseResponse usePoints(PointUseRequest request) {
        String lockKey = String.format("points:%d", request.userId());

        try {
            if (!redisLock.tryLock(lockKey, LOCK_TIMEOUT_MS)) {
                throw new ApplicationException(PointErrorCode.CONCURRENT_UPDATE);
            }

            Point point = pointRepository.findByUserId(request.userId())
                    .orElseGet(() -> Point.builder()
                            .userId(request.userId())
                            .pointBalance(0L)
                            .accumulatedPoints(0L)
                            .redeemedPoints(0L)
                            .build());

            if (point.getPointBalance() < request.pointsToUse()) {
                throw new ApplicationException(PointErrorCode.INSUFFICIENT_POINTS);
            }

            Point updatedPoint = request.toEntity(point);
            pointRepository.save(updatedPoint);

            return PointUseResponse.from(updatedPoint, request.pointsToUse());
        } finally {
            redisLock.unlock(lockKey);
        }
    }

}
