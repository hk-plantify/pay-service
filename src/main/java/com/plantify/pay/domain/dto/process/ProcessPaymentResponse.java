package com.plantify.pay.domain.dto.process;

import com.plantify.pay.domain.entity.Status;

import java.time.LocalDateTime;

public record ProcessPaymentResponse(
        Long transactionId,
        Long userId,
        Long paymentId,
        String orderId,
        String orderName,
        Long sellerId,
        Long amount,
        Long pointToUse,
        Status status,
        String redirectUri,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
