package com.plantify.pay.domain.dto.account;

import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.AccountStatus;
import com.plantify.pay.domain.entity.BankName;

import java.time.LocalDateTime;

public record AccountAdminResponse(
        Long accountId,
        Long userId,
        String accountNum,
        BankName bankName,
        AccountStatus accountStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AccountAdminResponse from(Account account) {
        return new AccountAdminResponse(
                account.getAccountId(),
                account.getPay().getUserId(),
                account.getAccountNum(),
                account.getBankName(),
                account.getAccountStatus(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}
