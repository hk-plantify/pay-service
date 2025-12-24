package com.plantify.pay.service.pay.orchestration;

import com.plantify.pay.client.TransactionServiceClient;
import com.plantify.pay.domain.dto.process.ProcessPaymentResponse;
import com.plantify.pay.domain.dto.process.TransactionResponse;
import com.plantify.pay.domain.dto.process.UpdateTransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CancellationOrchestrator {

    private final TransactionServiceClient transactionClient;

    @Transactional
    public ProcessPaymentResponse execute(UpdateTransactionRequest request) {

        TransactionResponse tx =
                transactionClient
                        .updatePayTransactionToCancellation(request)
                        .getData();

        return ProcessPaymentResponse.from(tx);
    }
}
