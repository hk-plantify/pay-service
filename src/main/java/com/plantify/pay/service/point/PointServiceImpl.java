package com.plantify.pay.service.point;

import com.plantify.pay.domain.entity.Point;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PointErrorCode;
import com.plantify.pay.global.util.DistributedLock;
import com.plantify.pay.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final DistributedLock distributedLock;

    @Override
    public void rewardPoints(Long userId, Long rewardPoints) {
        String lockKey = String.format("points:%d", userId);

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Point point = pointRepository.findByUserId(userId)
                    .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));

            point.updatePoint(rewardPoints);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    @Override
    public void PointsToUse(Long userId, Long pointToUse) {
        String lockKey = String.format("points:%d", userId);

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Point point = pointRepository.findByUserId(userId)
                    .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));
            point.validatePoint(pointToUse)
                    .usePoint(pointToUse);

        } finally {
            distributedLock.unlock(lockKey);
        }
    }
}
