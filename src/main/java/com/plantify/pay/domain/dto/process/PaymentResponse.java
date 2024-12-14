package com.plantify.pay.domain.dto.process;

import com.plantify.pay.domain.entity.Status;

import java.time.LocalDateTime;

public record PaymentResponse(
        Long transactionId,
        Long userId,
        Long paymentId,
        String orderId,
        String orderName,
        Long sellerId,
        Long amount,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String token,
        String redirectUri
) {

    public static PaymentResponse from(TransactionResponse transactionResponse, String token, String redirectUri) {
        return new PaymentResponse(
                transactionResponse.transactionId(),
                transactionResponse.userId(),
                transactionResponse.paymentId(),
                transactionResponse.orderId(),
                transactionResponse.orderName(),
                transactionResponse.sellerId(),
                transactionResponse.amount(),
                transactionResponse.status(),
                transactionResponse.createdAt(),
                transactionResponse.updatedAt(),
                token,
                redirectUri
        );
    }
}
