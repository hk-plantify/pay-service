package com.plantify.pay.service.pay.orchestration;

import com.plantify.pay.client.TransactionServiceClient;
import com.plantify.pay.domain.dto.process.ProcessPaymentResponse;
import com.plantify.pay.domain.dto.process.TransactionResponse;
import com.plantify.pay.domain.dto.process.UpdateTransactionRequest;
import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.domain.entity.Status;
import com.plantify.pay.service.pay.ledger.LedgerService;
import com.plantify.pay.service.settlement.PaySettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefundOrchestrator {

    private final LedgerService ledgerService;
    private final TransactionServiceClient transactionClient;
    private final PaySettlementService paySettlementService;

    @Transactional
    public ProcessPaymentResponse execute(UpdateTransactionRequest request) {

        TransactionResponse tx =
                transactionClient.updateTransactionToRefund(request).getData();

        PaySettlement settlement =
                paySettlementService.updateSettlementStatus(
                        request.orderId(), Status.REFUND
                );

        ledgerService.credit(
                tx.userId(),
                settlement.getAmount(),
                settlement.getPointUsed()
        );

        return ProcessPaymentResponse.from(tx);
    }
}
