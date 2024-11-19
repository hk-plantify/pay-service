package com.plantify.pay.service.pay;

public interface PayInternalService {

    void handleTransactionSuccess(Long transactionId, Long userId, Long amount);
    void handleTransactionFailure(Long transactionId, Long userId);
}
