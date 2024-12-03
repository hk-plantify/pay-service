package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.kafka.TransactionStatusMessage;

public interface PayTransactionStatusService {

    void processSuccessfulTransaction(TransactionStatusMessage message);
    void processFailedTransaction(TransactionStatusMessage message);
}
