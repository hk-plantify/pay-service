package com.plantify.pay.domain.dto.kafka;

import com.plantify.pay.domain.entity.Status;
import com.plantify.pay.domain.entity.TransactionType;

import java.time.LocalDateTime;

public record TransactionStatusResponse(
        Long transactionId,
        Long userId,
        Long sellerId,
        String transactionType,
        String status,
        Long amount,
        String reason,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
