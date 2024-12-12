package com.plantify.pay.domain.dto.pay;

import com.plantify.pay.domain.entity.PaySettlement;

import java.time.LocalDateTime;

public record ExternalSettlementResponse(
        String orderId,
        String orderName,
        LocalDateTime createdAt,
        Long amount
) {

    public static ExternalSettlementResponse from(PaySettlement paySettlement) {
        return new ExternalSettlementResponse(
                paySettlement.getOrderId(),
                paySettlement.getOrderName(),
                paySettlement.getCreatedAt(),
                paySettlement.getAmount()
        );
    }
}
