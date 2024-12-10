package com.plantify.pay.domain.dto.kafka;

public record PaymentRequest(
        Long userId,
        String orderId,
        String orderName,
        Long sellerId,
        Long amount
) {}