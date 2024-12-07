package com.plantify.pay.domain.dto.account;

import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.AccountStatus;
import com.plantify.pay.domain.entity.BankName;

import java.time.LocalDateTime;

public record AccountUserResponse(
        Long accountId,
        Long accountNum,
        BankName bankName,
        AccountStatus accountStatus,
        String accountHolder,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AccountUserResponse from(Account account) {
        return new AccountUserResponse(
                account.getAccountId(),
                account.getAccountNum(),
                account.getBankName(),
                account.getAccountStatus(),
                account.getAccountHolder(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}