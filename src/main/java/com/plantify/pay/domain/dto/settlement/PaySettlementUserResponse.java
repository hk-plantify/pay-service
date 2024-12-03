package com.plantify.pay.domain.dto.settlement;

import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.domain.entity.TransactionType;

import java.time.LocalDateTime;

public record PaySettlementUserResponse(
        Long paySettlementId,
        Long payId,
        TransactionType transactionType,
        Long transactionAmount,
        Long balanceAfter,
        LocalDateTime transactionDate
) {
    public static PaySettlementUserResponse from(PaySettlement paySettlement) {
        return new PaySettlementUserResponse(
                paySettlement.getPaySettlementId(),
                paySettlement.getPay().getPayId(),
                paySettlement.getTransactionType(),
                paySettlement.getTransactionAmount(),
                paySettlement.getBalanceAfter(),
                paySettlement.getTransactionDate()
        );
    }
}