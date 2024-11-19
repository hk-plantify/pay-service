package com.plantify.pay.service;

import com.plantify.pay.domain.dto.response.PointResponse;
import com.plantify.pay.domain.entity.Point;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PointErrorCode;
import com.plantify.pay.repository.PointRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService, PointInternalService {

    private final PointRepository pointRepository;
    private final AuthenticationService authenticationService;

    @Override
    public PointResponse getPoints() {
        Long userId = authenticationService.getUserId();

        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));

        return PointResponse.from(point);
    }

    @Override
    public PointResponse getPointsByUserId(Long userId) {
        authenticationService.validateAdminRole();

        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));

        return PointResponse.from(point);
    }

    @Override
    public PointResponse usePoints(Long pointsToUse) {
        Long userId = authenticationService.getUserId();

        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));

        if (point.getPointBalance() < pointsToUse) {
            throw new ApplicationException(PointErrorCode.INSUFFICIENT_POINTS);
        }

        Point updatedPoint = point.toBuilder()
                .pointBalance(point.getPointBalance() - pointsToUse)
                .redeemedPoints(point.getRedeemedPoints() + pointsToUse)
                .build();

        updatedPoint = pointRepository.save(updatedPoint);

        return PointResponse.from(updatedPoint);
    }

    @Override
    public void accumulatePoints(Long transactionAmount) {
        Long userId = authenticationService.getUserId();
        Long pointsToAccumulate = Math.round(transactionAmount * 0.005);

        Point point = pointRepository.findByUserId(userId)
                .orElse(Point.builder()
                        .userId(userId)
                        .pointBalance(0L)
                        .accumulatedPoints(0L)
                        .redeemedPoints(0L)
                        .build());

        Point updatedPoint = point.toBuilder()
                .pointBalance(point.getPointBalance() + pointsToAccumulate)
                .accumulatedPoints(point.getAccumulatedPoints() + pointsToAccumulate)
                .build();

        pointRepository.save(updatedPoint);
    }
}