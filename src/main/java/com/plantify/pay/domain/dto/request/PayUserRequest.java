package com.plantify.pay.domain.dto.request;

import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.PayStatus;

import java.time.LocalDateTime;

public record PayUserRequest(
        Long accountId,
        Long payNum,
        LocalDateTime expiryDate,
        Long balance,
        PayStatus payStatus
) {
    public Pay toEntity(Long userId) {
        return Pay.builder()
                .account(Account.builder().accountId(accountId).userId(userId).build())
                .payNum(payNum)
                .expiryDate(expiryDate)
                .balance(balance)
                .payStatus(payStatus)
                .build();
    }
}