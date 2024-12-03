package com.plantify.pay.domain.dto.pay;

import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.Status;

import java.time.LocalDateTime;

public record PayUserResponse(
        Long payId,
        Long accountId,
        Long payNum,
        Long balance,
        Status payStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static PayUserResponse from(Pay pay) {
        return new PayUserResponse(
                pay.getPayId(),
                pay.getAccount().getAccountId(),
                pay.getPayNum(),
                pay.getBalance(),
                pay.getPayStatus(),
                pay.getCreatedAt(),
                pay.getUpdatedAt()
        );
    }
}