package com.plantify.pay.service.pay;

import com.plantify.pay.client.TransactionServiceClient;
import com.plantify.pay.domain.dto.process.*;
import com.plantify.pay.domain.dto.pay.PayBalanceResponse;
import com.plantify.pay.domain.dto.settlement.PaySettlementRequest;
import com.plantify.pay.domain.entity.*;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.AuthErrorCode;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.global.exception.errorcode.PointErrorCode;
import com.plantify.pay.global.util.DistributedLock;
import com.plantify.pay.jwt.JwtProvider;
import com.plantify.pay.repository.AccountRepository;
import com.plantify.pay.repository.PayRepository;
import com.plantify.pay.repository.PointRepository;
import com.plantify.pay.service.point.PointService;
import com.plantify.pay.service.settlement.PaySettlementService;;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayServiceImpl implements PayService {

    private final DistributedLock distributedLock;
    private final JwtProvider jwtProvider;
    private final TransactionServiceClient transactionServiceClient;
    private final PointService pointService;
    private final PaySettlementService paySettlementService;
    private final PointRepository pointRepository;
    private final PayRepository payRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public PaymentResponse createPayTransaction(PendingTransactionRequest request) {
        String lockKey = String.format("pay:%d", request.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            payRepository.findByUserId(request.userId())
                    .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

            TransactionRequest transactionRequest = new TransactionRequest(
                    request.userId(),
                    request.sellerId(),
                    UUID.randomUUID().toString(),
                    request.orderName(),
                    request.amount(),
                    request.redirectUri()
            );

            TransactionResponse response = transactionServiceClient.createPendingTransaction(transactionRequest).getData();
            String token = jwtProvider.createAccessToken(response.transactionId());
            log.info("Created new pay transaction: {}", token);
            return PaymentResponse.from(response, token, request.redirectUri());
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    @Override
    @Transactional
    public TransactionStatusResponse getTransactionStatus(String token) {
        if (token == null || !jwtProvider.validateToken(token)) {
            throw new ApplicationException(AuthErrorCode.INVALID_TOKEN);
        }

        Long transactionId = jwtProvider.getClaims(token).get("id", Long.class);
        TransactionResponse response = transactionServiceClient.getTransactionById(transactionId).getData();

        Point point = pointRepository.findByUserId(response.userId())
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));
        Pay pay = payRepository.findByUserId(response.userId())
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));
        Account account = accountRepository.findFirstByPayUserIdOrderByCreatedAtDesc(response.userId())
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

        return new TransactionStatusResponse(
                response.transactionId(),
                response.userId(),
                response.sellerId(),
                response.orderId(),
                response.orderName(),
                response.status(),
                response.amount(),
                response.redirectUri(),
                response.createdAt(),
                response.updatedAt(),
                point.getPointBalance(),
                pay.getBalance(),
                account.getAccountNum(),
                account.getBankName()
        );
    }


    @Override
    @Transactional
    public ProcessPaymentResponse verifyAndProcessPayment(String token, Long pointToUse) {
        if (token == null || !jwtProvider.validateToken(token)) {
            throw new ApplicationException(AuthErrorCode.INVALID_TOKEN);
        }

        Long transactionId = jwtProvider.getClaims(token).get("id", Long.class);
        TransactionResponse transactionResponse = transactionServiceClient.getTransactionById(transactionId).getData();
        Long userId = transactionResponse.userId();
        long finalAmount = transactionResponse.amount() - pointToUse;

        Pay pay = payRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND))
                .validatePay(finalAmount).success(finalAmount);
        payRepository.save(pay);
        pointService.usePoints(userId, pointToUse);

        transactionServiceClient.updateTransactionToSuccess(new PayTransactionRequest(transactionId));

        paySettlementService.savePaySettlement(new PaySettlementRequest(
                userId,
                transactionResponse.orderId(),
                transactionResponse.orderName(),
                finalAmount,
                Status.PAYMENT,
                pointToUse
        ));

        return new ProcessPaymentResponse(
                transactionId,
                transactionResponse.userId(),
                transactionResponse.paymentId(),
                transactionResponse.orderId(),
                transactionResponse.orderName(),
                transactionResponse.sellerId(),
                transactionResponse.amount(),
                pointToUse,
                transactionResponse.status(),
                transactionResponse.redirectUri(),
                transactionResponse.createdAt(),
                transactionResponse.updatedAt()
        );
    }

    @Override
    @Transactional
    public ProcessPaymentResponse refund(UpdateTransactionRequest request) {
        String lockKey = String.format("refund:%d", request.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            TransactionResponse transactionResponse = transactionServiceClient.updateTransactionToRefund(request).getData();

            PaySettlement paySettlement = paySettlementService.updateSettlementStatus(request.orderId(), transactionResponse.status());

            Pay pay = payRepository.findByUserId(request.userId())
                    .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND))
                    .updatedBalance(paySettlement.getAmount());
            payRepository.save(pay);

            pointService.addPoints(request.userId(), paySettlement.getPointUsed());

            return new ProcessPaymentResponse(
                    transactionResponse.transactionId(),
                    transactionResponse.userId(),
                    transactionResponse.paymentId(),
                    transactionResponse.orderId(),
                    transactionResponse.orderName(),
                    transactionResponse.sellerId(),
                    transactionResponse.amount(),
                    0L,
                    Status.REFUND,
                    transactionResponse.redirectUri(),
                    transactionResponse.createdAt(),
                    transactionResponse.updatedAt()
            );
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    @Override
    @Transactional
    public ProcessPaymentResponse cancellation(UpdateTransactionRequest request) {
        String lockKey = String.format("cancel:%d", request.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            TransactionResponse transactionResponse = transactionServiceClient.updatePayTransactionToCancellation(request).getData();

            Pay pay = payRepository.findByUserId(request.userId())
                    .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));
            payRepository.save(pay);

            return new ProcessPaymentResponse(
                    transactionResponse.transactionId(),
                    transactionResponse.userId(),
                    transactionResponse.paymentId(),
                    transactionResponse.orderId(),
                    transactionResponse.orderName(),
                    transactionResponse.sellerId(),
                    transactionResponse.amount(),
                    0L,
                    Status.CANCELLATION,
                    transactionResponse.redirectUri(),
                    transactionResponse.createdAt(),
                    transactionResponse.updatedAt()
            );
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    @Override
    public PayBalanceResponse checkPayBalance(PayBalanceRequest request) {
        Pay pay = payRepository.findByUserId(request.userId())
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

        if (request.amount() > pay.getBalance()) {
            throw new ApplicationException(PayErrorCode.INSUFFICIENT_BALANCE);
        }

        return new PayBalanceResponse(pay.getBalance());
    }

}
