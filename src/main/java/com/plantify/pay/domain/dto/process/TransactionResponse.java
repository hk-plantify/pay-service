package com.plantify.pay.domain.dto.process;

import com.plantify.pay.domain.entity.Status;

import java.time.LocalDateTime;

public record TransactionResponse(
        Long transactionId,
        Long userId,
        Long paymentId,
        String orderId,
        String orderName,
        Long amount,
        Long sellerId,
        Status status,
        String reason,
        String redirectUri,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
