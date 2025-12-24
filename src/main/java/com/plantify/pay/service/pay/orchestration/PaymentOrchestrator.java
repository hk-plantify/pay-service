package com.plantify.pay.service.pay.orchestration;

import com.plantify.pay.client.TransactionServiceClient;
import com.plantify.pay.domain.dto.process.*;
import com.plantify.pay.domain.dto.settlement.PaySettlementRequest;
import com.plantify.pay.domain.entity.Status;
import com.plantify.pay.jwt.JwtProvider;
import com.plantify.pay.service.pay.ledger.LedgerService;
import com.plantify.pay.service.settlement.PaySettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentOrchestrator {

    private final LedgerService ledgerService;
    private final TransactionServiceClient transactionClient;
    private final PaySettlementService paySettlementService;
    private final JwtProvider jwtProvider;

    public PaymentResponse createPending(PendingTransactionRequest request) {
        TransactionResponse tx =
                transactionClient.createPendingTransaction(
                        TransactionRequest.from(request)
                ).getData();

        String token = jwtProvider.createAccessToken(tx.transactionId());
        return PaymentResponse.from(tx, token, request.redirectUri());
    }

    public ProcessPaymentResponse execute(String token, long pointToUse) {

        Long transactionId = jwtProvider.getClaims(token).get("id", Long.class);
        TransactionResponse tx =
                transactionClient.getTransactionById(transactionId).getData();

        long finalAmount = tx.amount() - pointToUse;
        ledgerService.debit(tx.userId(), finalAmount, pointToUse);

        transactionClient.updateTransactionToSuccess(
                new PayTransactionRequest(transactionId)
        );

        paySettlementService.savePaySettlement(
                new PaySettlementRequest(
                        tx.userId(),
                        tx.orderId(),
                        tx.orderName(),
                        tx.amount(),
                        Status.PAYMENT,
                        pointToUse
                )
        );

        return ProcessPaymentResponse.from(tx);
    }
}
