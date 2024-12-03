package com.plantify.pay.domain.dto.point;

import com.plantify.pay.domain.entity.Point;

public record PointUseRequest(
        Long userId,
        Long pointsToUse
) {

    public Point toEntity(Point existingPoint) {
        return existingPoint.toBuilder()
                .pointBalance(existingPoint.getPointBalance() - pointsToUse)
                .redeemedPoints(existingPoint.getRedeemedPoints() + pointsToUse)
                .build();
    }
}