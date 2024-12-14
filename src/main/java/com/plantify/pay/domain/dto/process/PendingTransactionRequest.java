package com.plantify.pay.domain.dto.process;

import com.plantify.pay.domain.entity.Status;

public record PendingTransactionRequest(
        Long userId,
        Long sellerId,
        String orderName,
        Long amount,
        Status status,
        String redirectUri
){
}
