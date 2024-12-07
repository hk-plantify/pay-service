package com.plantify.pay.domain.dto.account;

import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.AccountStatus;
import com.plantify.pay.domain.entity.BankName;

public record AccountUserRequest(
        Long accountNum,
        BankName bankName,
        String accountHolder
) {

    public Account toEntity() {
        return Account.builder()
                .accountNum(accountNum)
                .bankName(bankName)
                .accountStatus(AccountStatus.ACTIVE)
                .accountHolder(accountHolder)
                .build();
    }
}