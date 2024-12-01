package com.plantify.pay.domain.dto.kafka;

import com.plantify.pay.domain.entity.TransactionType;

import java.time.LocalDateTime;

public record TransactionResponse(
        Long transactionId,
        Long userId,
        Long sellerId,
        TransactionType transactionType,
        String status,
        Long amount,
        Long balanceAfter,
        String reason,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static TransactionResponse from(TransactionResponse transactionResponse) {
        return new TransactionResponse(
                transactionResponse.transactionId(),
                transactionResponse.userId(),
                transactionResponse.sellerId(),
                transactionResponse.transactionType(),
                transactionResponse.status(),
                transactionResponse.amount(),
                transactionResponse.balanceAfter(),
                transactionResponse.reason(),
                transactionResponse.createdAt(),
                transactionResponse.updatedAt()
        );
    }
}
