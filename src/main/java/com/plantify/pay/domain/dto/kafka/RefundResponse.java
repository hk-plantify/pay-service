package com.plantify.pay.domain.dto.kafka;

import com.plantify.pay.domain.entity.TransactionType;

import java.time.LocalDateTime;

public record RefundResponse(
        Long transactionId,
        Long userId,
        Long sellerId,
        String transactionType,
        String status,
        Long amount,
        Long balanceAfter,
        String reason,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static RefundResponse from(RefundResponse refundResponse) {
        return new RefundResponse(
                refundResponse.transactionId(),
                refundResponse.userId(),
                refundResponse.sellerId(),
                refundResponse.transactionType(),
                refundResponse.status(),
                refundResponse.amount(),
                refundResponse.balanceAfter(),
                refundResponse.reason(),
                refundResponse.createdAt(),
                refundResponse.updatedAt()
        );
    }
}
