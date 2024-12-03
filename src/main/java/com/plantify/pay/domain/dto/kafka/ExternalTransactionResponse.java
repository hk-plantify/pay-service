package com.plantify.pay.domain.dto.kafka;

import com.plantify.pay.domain.entity.TransactionType;

import java.time.LocalDateTime;

public record ExternalTransactionResponse(
        Long transactionId,
        Long userId,
        Long sellerId,
        TransactionType transactionType,
        String status,
        Long amount,
        Long balanceAfter,
        String reason,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String token
) {

    public static ExternalTransactionResponse from(TransactionResponse transactionResponse, String token) {
        return new ExternalTransactionResponse(
                transactionResponse.transactionId(),
                transactionResponse.userId(),
                transactionResponse.sellerId(),
                transactionResponse.transactionType(),
                transactionResponse.status(),
                transactionResponse.amount(),
                transactionResponse.balanceAfter(),
                transactionResponse.reason(),
                transactionResponse.createdAt(),
                transactionResponse.updatedAt(),
                token
        );
    }

}
