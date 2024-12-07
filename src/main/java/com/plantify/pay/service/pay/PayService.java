package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.kafka.PaymentResponse;
import com.plantify.pay.domain.dto.kafka.RefundResponse;
import com.plantify.pay.domain.dto.kafka.TransactionRequest;
import com.plantify.pay.domain.dto.kafka.TransactionStatusResponse;

public interface PayService {

    PaymentResponse payment(TransactionRequest request);
    TransactionStatusResponse getTransactionStatus(String token);
    RefundResponse refund(TransactionRequest request);

}
