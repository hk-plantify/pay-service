package com.plantify.pay.domain.dto.kafka;

import com.plantify.pay.domain.entity.Status;

public record TransactionStatusMessage(
        Long transactionId,
        Long userId,
        Long orderId,
        Long amount,
        Status status
){}