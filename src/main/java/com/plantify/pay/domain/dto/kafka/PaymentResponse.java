package com.plantify.pay.domain.dto.kafka;

import com.plantify.pay.domain.entity.Status;
import com.plantify.pay.domain.entity.TransactionType;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public record PaymentResponse(
        Long transactionId,
        Long userId,
        Long paymentId,
        Long orderId,
        String orderName,
        Long sellerId,
        Long amount,
        String transactionType,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String token
) {

    public static PaymentResponse from(TransactionResponse transactionResponse, String token) {
        log.info("Transaction ID: {}", transactionResponse.transactionId());
        return new PaymentResponse(
                transactionResponse.transactionId(),
                transactionResponse.userId(),
                transactionResponse.paymentId(),
                transactionResponse.orderId(),
                transactionResponse.orderName(),
                transactionResponse.sellerId(),
                transactionResponse.amount(),
                transactionResponse.transactionType(),
                transactionResponse.status(),
                transactionResponse.createdAt(),
                transactionResponse.updatedAt(),
                token
        );
    }
}
