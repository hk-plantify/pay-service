package com.plantify.pay.domain.dto.kafka;

import com.plantify.pay.domain.entity.BankName;

import java.time.LocalDateTime;

public record TransactionStatusResponse(
        Long transactionId,
        Long userId,
        Long sellerId,
        String orderId,
        String orderName,
        String transactionType,
        String status,
        Long amount,
        String redirectUri,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long pointBalance,
        Long balance,
        Long accountNum,
        BankName bankName
) {
}