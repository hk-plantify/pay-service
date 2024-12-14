package com.plantify.pay.domain.dto.point;

import com.plantify.pay.domain.entity.Point;

public record PointUserResponse(
        Long userId,
        Long pointBalance
) {
    public static PointUserResponse from(Point point) {
        return new PointUserResponse(
                point.getUserId(),
                point.getPointBalance()
        );
    }
}
