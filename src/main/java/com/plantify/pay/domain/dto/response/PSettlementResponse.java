package com.plantify.pay.domain.dto.response;

import com.plantify.pay.domain.entity.PSettlement;

import java.time.LocalDateTime;

public record PSettlementResponse(
        Long settlementId,
        Long payId,
        String transactionType,
        Long transactionAmount,
        Long balanceAfter,
        LocalDateTime transactionDate
) {

    public static PSettlementResponse from(PSettlement settlement) {
        return new PSettlementResponse(
                settlement.getPSettlementId(),
                settlement.getPay().getPayId(),
                settlement.getTransactionType().name(),
                settlement.getTransactionAmount(),
                settlement.getBalanceAfter(),
                settlement.getTransactionDate()
        );
    }
}