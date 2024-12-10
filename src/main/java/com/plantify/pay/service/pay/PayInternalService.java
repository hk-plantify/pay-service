package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.kafka.*;
import com.plantify.pay.domain.entity.Pay;

public interface PayInternalService {

    Pay rechargeBalance(Long userId, Long amount);
    PaymentResponse payTransaction(TransactionRequest request, boolean isExternal);
    RefundResponse refundTransaction(TransactionRequest request);
    PaymentResponse createPayTransaction(TransactionRequest request);
    ProcessPaymentResponse processPayment(String token, Long pointToUse);
    RefundResponse createRefundTransaction(TransactionRequest request);
}
