package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.pay.PayBalanceResponse;
import com.plantify.pay.domain.dto.process.*;

public interface PayService {

    PaymentResponse createPayTransaction(PendingTransactionRequest request);
    TransactionStatusResponse getTransactionStatus(String token);
    ProcessPaymentResponse verifyAndProcessPayment(String token, Long pointToUse);
    ProcessPaymentResponse refund(UpdateTransactionRequest request);
    ProcessPaymentResponse cancellation(UpdateTransactionRequest request);
    PayBalanceResponse checkPayBalance(PayBalanceRequest request);
}