package com.plantify.pay.domain.dto.process;

public record UpdateTransactionRequest(
        String orderId,
        String reason
) {
}
