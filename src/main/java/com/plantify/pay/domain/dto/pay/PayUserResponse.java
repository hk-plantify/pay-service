package com.plantify.pay.domain.dto.pay;

import com.plantify.pay.domain.entity.Pay;

import java.time.LocalDateTime;

public record PayUserResponse(
        Long payNum,
        Long balance,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static PayUserResponse from(Pay pay) {
        return new PayUserResponse(
                pay.getPayNum(),
                pay.getBalance(),
                pay.getCreatedAt(),
                pay.getUpdatedAt()
        );
    }
}