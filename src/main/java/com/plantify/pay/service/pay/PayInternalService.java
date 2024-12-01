package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.kafka.TransactionRequest;
import com.plantify.pay.domain.dto.kafka.TransactionResponse;
import com.plantify.pay.domain.entity.Pay;

public interface PayInternalService {

    Pay rechargeBalance(Long userId, Long amount);
    TransactionResponse executeTransaction(Long sellerId, TransactionRequest request, boolean isExternal);
    void validatePayBalance(Long userId, Long amount);
    TransactionResponse createTransaction(Long sellerId, TransactionRequest request);
    TransactionResponse refundTransaction(Long sellerId, TransactionRequest request);
}
