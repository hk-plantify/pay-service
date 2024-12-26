package com.plantify.pay.domain.dto.pay;

public record PayUserRequest(
        Long accountId,
        Long balance
) {
}