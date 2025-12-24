package com.plantify.pay.service.pay.event;

import com.plantify.pay.domain.dto.process.TransactionStatusMessage;

public interface PayTransactionStatusService {

    void processSuccessfulTransaction(TransactionStatusMessage message);
    void processFailedTransaction(TransactionStatusMessage message);
}
