package com.plantify.pay.domain.dto.kafka;

import com.plantify.pay.domain.entity.Status;
import com.plantify.pay.domain.entity.TransactionType;

import java.time.LocalDateTime;

public record TransactionResponse(
        Long transactionId,
        Long userId,
        Long paymentId,
        Long orderId,
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
