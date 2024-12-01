package com.plantify.pay.domain.dto.point;

import com.plantify.pay.domain.entity.Point;

public record PointUserResponse(
        Long userId,
        Long pointBalance,
        Long accumulatedPoints,
        Long redeemedPoints
) {
    public static PointUserResponse from(Point point) {
        return new PointUserResponse(
                point.getUserId(),
                point.getPointBalance() != null ? point.getPointBalance() : 0L,
                point.getAccumulatedPoints() != null ? point.getAccumulatedPoints() : 0L,
                point.getRedeemedPoints() != null ? point.getRedeemedPoints() : 0L
        );
    }
}
