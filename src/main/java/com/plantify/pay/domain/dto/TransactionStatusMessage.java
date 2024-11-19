package com.plantify.pay.domain.dto;

public record TransactionStatusMessage(
        Long transactionId,
        Long userId,
        Long amount,
        String status
) {}