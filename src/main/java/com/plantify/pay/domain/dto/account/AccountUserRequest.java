package com.plantify.pay.domain.dto.account;

import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.AccountStatus;

public record AccountUserRequest(
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

    public Account updatedAccount(Account account) {
        return account.toBuilder()
                .accountNum(this.accountNum())
                .bankName(this.bankName())
                .accountStatus(this.accountStatus())
                .accountHolder(this.accountHolder())
                .build();
    }
}