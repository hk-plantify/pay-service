package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.kafka.*;

public interface PayTransactionService {

    TransactionResponse createTransaction(Long sellerId, TransactionRequest request);
    ExternalTransactionResponse createExternalTransaction(Long sellerId, TransactionRequest request);
    TransactionResponse refundTransaction(Long sellerId, TransactionRequest request);
    TransactionStatusResponse getTransactionStatus(String token);
}
