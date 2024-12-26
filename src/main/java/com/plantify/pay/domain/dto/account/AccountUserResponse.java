package com.plantify.pay.domain.dto.account;

import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.AccountStatus;
import com.plantify.pay.domain.entity.BankName;

import java.time.LocalDateTime;

public record AccountUserResponse(
        Long accountId,
        String accountNum,
        BankName bankName,
        AccountStatus accountStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AccountUserResponse from(Account account) {
        return new AccountUserResponse(
                account.getAccountId(),
                account.getAccountNum(),
                account.getBankName(),
                account.getAccountStatus(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}