package com.plantify.pay.domain.dto.process;

public record PaymentRequest(
        String orderId,
        String orderName,
        Long sellerId,
        Long amount,
        String redirectUri
) {}