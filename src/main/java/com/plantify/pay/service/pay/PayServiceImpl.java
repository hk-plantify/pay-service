package com.plantify.pay.service.pay;

import com.plantify.pay.client.TransactionServiceClient;
import com.plantify.pay.domain.dto.kafka.*;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.AuthErrorCode;
import com.plantify.pay.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayServiceImpl implements PayService {

    private final JwtProvider jwtProvider;
    private final TransactionServiceClient transactionServiceClient;
    private final PayInternalService payInternalService;

    @Override
    @Transactional
    public PaymentResponse payment(TransactionRequest request) {
        return payInternalService.payTransaction(request, true);
    }

    @Override
    @Transactional
    public TransactionStatusResponse getTransactionStatus(String token) {
        if (token == null || !jwtProvider.validateToken(token)) {
            throw new ApplicationException(AuthErrorCode.INVALID_TOKEN);
        }

        Long transactionId = jwtProvider.getClaims(token).get("id", Long.class);
        TransactionResponse response = transactionServiceClient.getTransactionById(transactionId).getData();

        return new TransactionStatusResponse(
                response.transactionId(),
                response.userId(),
                response.sellerId(),
                response.transactionType(),
                response.status(),
                response.amount(),
                response.reason(),
                response.createdAt(),
                response.updatedAt()
        );
    }

    @Override
    @Transactional
    public RefundResponse refund(TransactionRequest request) {
        return payInternalService.refundTransaction(request);
    }
}
