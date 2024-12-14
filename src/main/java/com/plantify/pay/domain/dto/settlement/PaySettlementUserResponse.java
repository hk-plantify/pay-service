package com.plantify.pay.domain.dto.settlement;

import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.domain.entity.Status;

import java.time.LocalDateTime;

public record PaySettlementUserResponse(
        Long paySettlementId,
        Status status,
        Long amount,
        String orderId,
        String orderName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PaySettlementUserResponse from(PaySettlement paySettlement) {
        return new PaySettlementUserResponse(
                paySettlement.getPaySettlementId(),
                paySettlement.getStatus(),
                paySettlement.getAmount(),
                paySettlement.getOrderId(),
                paySettlement.getOrderName(),
                paySettlement.getCreatedAt(),
                paySettlement.getUpdatedAt()
        );
    }
}