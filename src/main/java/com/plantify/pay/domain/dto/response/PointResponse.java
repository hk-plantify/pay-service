package com.plantify.pay.domain.dto.response;

import com.plantify.pay.domain.entity.Point;

import java.time.LocalDateTime;

public record PointResponse(
        Long pointId,
        Long userId,
        Long pointBalance,
        Long accumulatedPoints,
        Long redeemedPoints,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static PointResponse from(Point point) {
        return new PointResponse(
                point.getPointId(),
                point.getUserId(),
                point.getPointBalance(),
                point.getAccumulatedPoints(),
                point.getRedeemedPoints(),
                point.getCreatedAt(),
                point.getUpdatedAt()
        );
    }
}