package com.plantify.pay.domain.dto.kafka;

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
        String transactionType,
        String status,
        String redirectUri,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
