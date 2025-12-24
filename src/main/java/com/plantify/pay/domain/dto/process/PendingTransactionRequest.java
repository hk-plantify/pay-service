package com.plantify.pay.domain.dto.process;

public record PendingTransactionRequest(
        Long sellerId,
        String orderName,
        Long amount,
        String redirectUri
){
}
