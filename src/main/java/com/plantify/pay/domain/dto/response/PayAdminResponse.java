package com.plantify.pay.domain.dto.response;

import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.PayStatus;

import java.time.LocalDateTime;

public record PayAdminResponse(
        Long payId,
        Long accountId,
        Long payNum,
        LocalDateTime expiryDate,
        Long balance,
        PayStatus payStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PayAdminResponse from(Pay pay) {
        return new PayAdminResponse(
                pay.getPayId(),
                pay.getAccount().getAccountId(),
                pay.getPayNum(),
                pay.getExpiryDate(),
                pay.getBalance(),
                pay.getPayStatus(),
                pay.getCreatedAt(),
                pay.getUpdatedAt()
        );
    }
}
