package com.plantify.pay.service.point;

import com.plantify.pay.domain.entity.Point;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PointErrorCode;
import com.plantify.pay.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;

    @Override
    public void addPoints(Long userId, Long newPoints) {

        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));

        if (newPoints > 0) {
            point.addPoint(newPoints);
        }

        pointRepository.save(point);
    }

    @Override
    public void usePoints(Long userId, Long pointToUse) {

        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));

        point.validatePoint(pointToUse)
                .usePoint(pointToUse);

        pointRepository.save(point);
    }
}
