package com.plantify.pay.domain.dto.kafka;

public record TransactionStatusMessage(
        Long transactionId,
        Long userId,
        Long amount,
        String status
) {}