package com.plantify.pay.domain.dto.point;

import com.plantify.pay.domain.entity.Point;

public record PointAdminResponse(
        Long userId,
        Long pointBalance
) {
    public static PointAdminResponse from(Point point) {
        return new PointAdminResponse(
                point.getUserId(),
                point.getPointBalance()
        );
    }
}
