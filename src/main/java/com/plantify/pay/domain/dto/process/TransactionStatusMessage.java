package com.plantify.pay.domain.dto.process;

import com.plantify.pay.domain.entity.Status;

public record TransactionStatusMessage(
        Long transactionId,
        Long userId,
        String orderId,
        Long amount,
        Status status
){}