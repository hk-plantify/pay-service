package com.plantify.pay.domain.dto.response;

import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.AccountStatus;

import java.time.LocalDateTime;

public record AccountResponse(
        Long accountId,
        Long userId,
        Long accountNum,
        String bankName,
        AccountStatus accountStatus,
        String accountHolder,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getAccountId(),
                account.getUserId(),
                account.getAccountNum(),
                account.getBankName(),
                account.getAccountStatus(),
                account.getAccountHolder(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}
