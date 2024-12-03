package com.plantify.pay.domain.dto.point;

import com.plantify.pay.domain.entity.Point;

public record PointUseResponse(
        Long userId,
        Long remainingPoints,
        Long usedPoints
) {

    public static PointUseResponse from(Point point, Long usedPoints) {
        return new PointUseResponse(
                point.getUserId(),
                point.getPointBalance(),
                usedPoints
        );
    }
}
