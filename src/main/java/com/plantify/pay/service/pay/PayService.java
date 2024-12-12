package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.kafka.*;
import com.plantify.pay.domain.dto.pay.PayBalanceResponse;

public interface PayService {

    PaymentResponse initiatePayment(TransactionRequest request);
    TransactionStatusResponse getTransactionStatus(String authorizationHeader);
    ProcessPaymentResponse verifyAndProcessPayment(String authorizationHeader, Long pointToUse);
    RefundResponse refund(TransactionRequest request);
    PayBalanceResponse checkPayBalance(PayBalanceRequest request);
}