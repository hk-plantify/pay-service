package com.plantify.pay.domain.dto.request;

public record PSettlementRequest(
        Long payId,
        String transactionType,
        Long transactionAmount,
        Long balanceAfter
) {}