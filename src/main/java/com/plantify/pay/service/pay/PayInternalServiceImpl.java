package com.plantify.pay.service.pay;

import com.plantify.pay.client.TransactionServiceClient;
import com.plantify.pay.domain.dto.kafka.*;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.domain.entity.Status;
import com.plantify.pay.domain.entity.TransactionType;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.AuthErrorCode;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.global.util.DistributedLock;
import com.plantify.pay.jwt.JwtProvider;
import com.plantify.pay.repository.PayRepository;
import com.plantify.pay.repository.PaySettlementRepository;
import com.plantify.pay.service.point.PointService;
import com.plantify.pay.service.settlement.PaySettlementUserService;
import com.plantify.pay.service.settlement.PaySettlementUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayInternalServiceImpl implements PayInternalService {

    private final PayRepository payRepository;
    private final DistributedLock distributedLock;
    private final JwtProvider jwtProvider;
    private final TransactionServiceClient transactionServiceClient;
    private final PointService pointService;
    private final PaySettlementUserService paySettlementUserService;

    @Override
    public Pay rechargeBalance(Long userId, Long amount) {
        String lockKey = String.format("pay:%d", userId);

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Pay pay = payRepository.findByUserId(userId)
                    .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

            pay.validateAmount(amount).updatedBalance(amount);
            payRepository.save(pay);

            paySettlementUserService.savePaySettlement(pay, TransactionType.DEPOSIT, amount);

            return pay;
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    @Override
    public PaymentResponse payTransaction(TransactionRequest request, boolean isExternal) {
        String lockKey = String.format("pay:%d", request.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            return createPayTransaction(request);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    @Override
    public PaymentResponse createPayTransaction(TransactionRequest request) {
        PaymentRequest paymentRequest = new PaymentRequest(
                request.userId(),
                UUID.randomUUID().toString(),
                request.orderName(),
                request.sellerId(),
                request.amount()
        );

        TransactionResponse response = transactionServiceClient.createPendingTransaction(paymentRequest).getData();
        String token = jwtProvider.createAccessToken(response.transactionId());

        return PaymentResponse.from(response, token);
    }

    @Override
    public ProcessPaymentResponse processPayment(String token, Long pointToUse) {
        if (token == null || !jwtProvider.validateToken(token)) {
            throw new ApplicationException(AuthErrorCode.INVALID_TOKEN);
        }

        Long transactionId = jwtProvider.getClaims(token).get("id", Long.class);
        TransactionResponse transactionResponse = transactionServiceClient.getTransactionById(transactionId).getData();

        Long userId = transactionResponse.userId();
        Long finalAmount = transactionResponse.amount() - pointToUse;

        if (pointToUse > 0) {
            pointService.PointsToUse(userId, pointToUse);
        }

        Pay pay = payRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));
        pay.validatePay(finalAmount).success(finalAmount);
        payRepository.save(pay);

        paySettlementUserService.savePaySettlement(pay, TransactionType.PAYMENT, finalAmount);

        TransactionResponse response = transactionServiceClient.createPayTransaction(
                new PayTransactionRequest(transactionId)).getData();

        return new ProcessPaymentResponse(
                response.transactionId(),
                response.userId(),
                response.paymentId(),
                response.orderId(),
                response.orderName(),
                response.sellerId(),
                response.amount(),
                pointToUse,
                response.transactionType(),
                response.status(),
                response.createdAt(),
                response.updatedAt()
        );
    }

    @Override
    public RefundResponse refundTransaction(TransactionRequest request) {
        String lockKey = String.format("refund:%d", request.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Pay pay = payRepository.findByUserId(request.userId())
                    .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

            pay.validateAmount(request.amount()).updatedBalance(request.amount());
            payRepository.save(pay);

            return createRefundTransaction(request);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    @Override
    public RefundResponse createRefundTransaction(TransactionRequest request) {
        return null;
    }
}