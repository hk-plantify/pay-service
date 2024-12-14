package com.plantify.pay.domain.dto.process;

import com.plantify.pay.domain.entity.BankName;
import com.plantify.pay.domain.entity.Status;

import java.time.LocalDateTime;

public record TransactionStatusResponse(
        Long transactionId,
        Long userId,
        Long sellerId,
        String orderId,
        String orderName,
        Status status,
        Long amount,
        String redirectUri,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long pointBalance,
        Long balance,
        String accountNum,
        BankName bankName
) {
}