package com.plantify.pay.domain.dto.kafka;

public record TransactionRequest(
        Long userId,
        Long sellerId,
        String orderName,
        Long amount,
        String transactionType,
        String reason,
        String redirectUri
) {
}
