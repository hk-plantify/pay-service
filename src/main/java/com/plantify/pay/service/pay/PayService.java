package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.kafka.*;
import com.plantify.pay.domain.dto.pay.PayBalanceResponse;

public interface PayService {

    PaymentResponse initiatePayment(TransactionRequest request);
    TransactionStatusResponse getTransactionStatus(String token);
    ProcessPaymentResponse verifyAndProcessPayment(String token, Long pointToUse);
    RefundResponse refund(TransactionRequest request);
    PayBalanceResponse checkPayBalance(Long amount);
}
