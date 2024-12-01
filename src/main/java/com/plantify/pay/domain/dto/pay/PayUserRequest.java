package com.plantify.pay.domain.dto.pay;

import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.Status;

import java.time.LocalDateTime;

public record PayUserRequest(
        Long accountId,
        Long payNum,
        LocalDateTime expiryDate,
        Long balance,
        Status payStatus
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

    public Pay updatedPay(Pay pay) {
        return pay.toBuilder()
                .balance(pay.getBalance() + this.balance())
                .build();
    }

}