package com.plantify.pay.domain.dto.request;

import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.AccountStatus;

public record AccountRequest(
        Long accountNum,
        String bankName,
        AccountStatus accountStatus,
        String accountHolder
) {

    public Account toEntity(Long userId) {
        return Account.builder()
                .userId(userId)
                .accountNum(accountNum)
                .bankName(bankName)
                .accountStatus(accountStatus)
                .accountHolder(accountHolder)
                .build();
    }
}
