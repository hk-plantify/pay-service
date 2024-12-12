package com.plantify.pay.domain.dto.settlement;

import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.domain.entity.TransactionType;

public record PaySettlementRequest(
        Long userId,
        TransactionType transactionType,
        String orderId,
        String orderName,
        Long amount
) {

    public PaySettlement toEntity(Pay pay) {
        return PaySettlement.builder()
                .pay(pay)
                .transactionType(transactionType)
                .orderId(orderId)
                .orderName(orderName)
                .amount(amount)
                .build();
    }
}