package com.plantify.pay.domain.dto.settlement;

import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.domain.entity.Status;

public record PaySettlementRequest(
        Long userId,
        String orderId,
        String orderName,
        Long amount,
        Status status,
        Long pointUsed
) {

    public PaySettlement toEntity(Pay pay) {
        return PaySettlement.builder()
                .pay(pay)
                .status(status)
                .orderId(orderId)
                .orderName(orderName)
                .amount(amount)
                .pointUsed(pointUsed)
                .build();
    }
}