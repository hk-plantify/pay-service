package com.plantify.pay.domain.dto.account;

import com.plantify.pay.domain.entity.AccountStatus;

public record AccountAdminRequest(
        Long userId,
        Long accountNum,
        String bankName,
        AccountStatus accountStatus,
        String accountHolder
) {
}