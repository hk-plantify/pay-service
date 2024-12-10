package com.plantify.pay.domain.dto.kafka;

import java.time.LocalDateTime;

public record TransactionResponse(
        Long transactionId,
        Long userId,
        Long paymentId,
        String orderId,
        String orderName,
        Long amount,
        Long sellerId,
        String transactionType,
        String status,
        String reason,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
