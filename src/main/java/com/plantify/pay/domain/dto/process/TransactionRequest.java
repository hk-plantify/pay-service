package com.plantify.pay.domain.dto.process;

public record TransactionRequest(
        Long userId,
        Long sellerId,
        String orderId,
        String orderName,
        Long amount,
        String redirectUri
) {
}
