package com.plantify.pay.domain.dto.account;

import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.AccountStatus;
import com.plantify.pay.domain.entity.BankName;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AccountUserRequest(
        @Pattern(regexp = "\\d{10,14}", message = "계좌번호는 10~14자리 입니다.")
        Long accountNum,
        BankName bankName,
        @Size(min = 2, message = "계좌이름은 최소 2자리 입니다.")
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