package com.plantify.pay.domain.dto.pay;

import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.Status;

import java.time.LocalDateTime;

public record PayAdminResponse(
        Long payId,
        Long userId,
        Long payNum,
        LocalDateTime expiryDate,
        Long balance,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PayAdminResponse from(Pay pay) {
        return new PayAdminResponse(
                pay.getPayId(),
                pay.getUserId(),
                pay.getPayNum(),
                pay.getExpiryDate(),
                pay.getBalance(),
                pay.getCreatedAt(),
                pay.getUpdatedAt()
        );
    }
}
