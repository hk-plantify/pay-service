package com.plantify.pay.domain.dto.request;

public record PointRequest(
        Long userId,
        Long amount
) {}