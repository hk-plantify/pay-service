package com.plantify.pay.domain.dto.kafka;

public record PaymentRequest(
        Long userId,
        Long orderId,
        String orderName,
        Long sellerId,
        Long amount
) {}