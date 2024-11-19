package com.plantify.pay.domain.dto.request;

public record PointUseRequest(
        Long userId,
        Long pointsToUse
) {}