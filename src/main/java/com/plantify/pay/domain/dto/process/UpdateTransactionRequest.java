package com.plantify.pay.domain.dto.process;

public record UpdateTransactionRequest(
        Long userId,
        String orderId,
        String reason
) {
}
