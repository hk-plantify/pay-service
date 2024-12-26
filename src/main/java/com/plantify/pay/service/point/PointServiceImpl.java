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
    public void addPoints(Long userId, Long newPoints) {
        String lockKey = String.format("points:%d", userId);

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Point point = pointRepository.findByUserId(userId)
                    .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND))
                    .addPoint(newPoints);
            pointRepository.save(point);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    @Override
    public void usePoints(Long userId, Long pointToUse) {
        String lockKey = String.format("points:%d", userId);

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Point point = pointRepository.findByUserId(userId)
                    .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND))
                    .validatePoint(pointToUse)
                    .usePoint(pointToUse);
            pointRepository.save(point);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }
}
