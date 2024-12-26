package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.process.TransactionStatusMessage;

public interface PayTransactionStatusService {

    void processSuccessfulTransaction(TransactionStatusMessage message);
    void processFailedTransaction(TransactionStatusMessage message);
}
