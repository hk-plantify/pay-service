package com.plantify.pay.domain.dto.kafka;

import com.plantify.pay.domain.entity.TransactionType;

public record TransactionRequest(
        Long userId,
        Long sellerId,
        TransactionType transactionType,
        Long amount,
        String reason
) {
}
