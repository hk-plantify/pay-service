package com.plantify.pay.domain.dto.settlement;

import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.domain.entity.TransactionType;

import java.time.LocalDateTime;

public record PaySettlementAdminResponse(
        Long paySettlementId,
        Long payId,
        Long userId,
        TransactionType transactionType,
        Long transactionAmount,
        Long balanceAfter,
        LocalDateTime transactionDate
) {
    public static PaySettlementAdminResponse from(PaySettlement paySettlement) {
        return new PaySettlementAdminResponse(
                paySettlement.getPaySettlementId(),
                paySettlement.getPay().getPayId(),
                paySettlement.getPay().getAccount().getUserId(),
                paySettlement.getTransactionType(),
                paySettlement.getTransactionAmount(),
                paySettlement.getBalanceAfter(),
                paySettlement.getTransactionDate()
        );
    }
}