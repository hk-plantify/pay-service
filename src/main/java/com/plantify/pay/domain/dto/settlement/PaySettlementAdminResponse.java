package com.plantify.pay.domain.dto.settlement;

import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.domain.entity.Status;

import java.time.LocalDateTime;

public record PaySettlementAdminResponse(
        Long paySettlementId,
        Long userId,
        Status status,
        Long amount,
        String orderId,
        String orderName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PaySettlementAdminResponse from(PaySettlement paySettlement) {
        return new PaySettlementAdminResponse(
                paySettlement.getPaySettlementId(),
                paySettlement.getPay().getUserId(),
                paySettlement.getStatus(),
                paySettlement.getAmount(),
                paySettlement.getOrderId(),
                paySettlement.getOrderName(),
                paySettlement.getCreatedAt(),
                paySettlement.getUpdatedAt()
        );
    }
}