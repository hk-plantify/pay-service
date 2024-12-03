package com.plantify.pay.domain.dto.kafka;

import com.plantify.pay.domain.entity.TransactionType;

import java.time.LocalDateTime;

public record TransactionStatusResponse(
        Long transactionId,
        Long userId,
        Long sellerId,
        TransactionType transactionType,
        String status,
        Long amount,
        String reason,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static TransactionStatusResponse from(TransactionResponse transactionResponse) {
        return new TransactionStatusResponse(
                transactionResponse.transactionId(),
                transactionResponse.userId(),
                transactionResponse.sellerId(),
                transactionResponse.transactionType(),
                transactionResponse.status(),
                transactionResponse.amount(),
                transactionResponse.reason(),
                transactionResponse.createdAt(),
                transactionResponse.updatedAt()
        );
    }

}
