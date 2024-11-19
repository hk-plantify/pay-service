package com.plantify.pay.domain.dto.response;

public record PointUseResponse(
        Long userId,
        Long remainingPoints,
        Long usedPoints
) {}
