package com.plantify.pay.service.point;

import com.plantify.pay.domain.dto.request.PointUseRequest;
import com.plantify.pay.domain.dto.response.PointUseResponse;
import com.plantify.pay.global.exception.errorcode.PointErrorCode;
import com.plantify.pay.util.UserInfoProvider;
import com.plantify.pay.domain.dto.response.PointUserResponse;
import com.plantify.pay.domain.entity.Point;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointUserServiceImpl implements PointUserService {

    private final PointRepository pointRepository;
    private final UserInfoProvider userInfoProvider;

    @Override
    public PointUserResponse getUserPoints() {
        Long userId = userInfoProvider.getUserInfo().userId();
        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));
        return PointUserResponse.from(point);
    }

    @Override
    public PointUseResponse usePoints(PointUseRequest request) {
        Point point = pointRepository.findByUserId(request.userId())
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));

        if (point.getPointBalance() < request.pointsToUse()) {
            throw new ApplicationException(PointErrorCode.INSUFFICIENT_POINTS);
        }

        point = point.toBuilder()
                .pointBalance(point.getPointBalance() - request.pointsToUse())
                .redeemedPoints(point.getRedeemedPoints() + request.pointsToUse())
                .build();
        pointRepository.save(point);

        return new PointUseResponse(request.userId(), point.getPointBalance(), request.pointsToUse());
    }

    @Override
    public void rewardPoints(Long userId, Long rewardPoints) {
        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));

        point = point.toBuilder()
                .pointBalance(point.getPointBalance() + rewardPoints)
                .accumulatedPoints(point.getAccumulatedPoints() + rewardPoints)
                .build();
        pointRepository.save(point);
    }

}
