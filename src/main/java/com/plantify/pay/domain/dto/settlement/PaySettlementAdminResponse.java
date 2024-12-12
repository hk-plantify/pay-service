package com.plantify.pay.domain.dto.settlement;

import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.domain.entity.TransactionType;

import java.time.LocalDateTime;

public record PaySettlementAdminResponse(
        Long paySettlementId,
        Long payId,
        TransactionType transactionType,
        Long amount,
        String orderId,
        String orderName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PaySettlementAdminResponse from(PaySettlement paySettlement) {
        return new PaySettlementAdminResponse(
                paySettlement.getPaySettlementId(),
                paySettlement.getPay().getPayId(),
                paySettlement.getTransactionType(),
                paySettlement.getAmount(),
                paySettlement.getOrderId(),
                paySettlement.getOrderName(),
                paySettlement.getCreatedAt(),
                paySettlement.getUpdatedAt()
        );
    }
}