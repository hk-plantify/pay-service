package com.plantify.pay.domain.dto.response;

import com.plantify.pay.domain.entity.Point;

public record PointAdminResponse(
        Long userId,
        Long pointBalance,
        Long accumulatedPoints,
        Long redeemedPoints
) {
    public static PointAdminResponse from(Point point) {
        return new PointAdminResponse(
                point.getUserId(),
                point.getPointBalance(),
                point.getAccumulatedPoints(),
                point.getRedeemedPoints()
        );
    }
}
