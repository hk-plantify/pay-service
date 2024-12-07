package com.plantify.pay.service.pay;

import com.plantify.pay.client.TransactionServiceClient;
import com.plantify.pay.domain.dto.kafka.*;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.Status;
import com.plantify.pay.domain.entity.TransactionType;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.global.util.DistributedLock;
import com.plantify.pay.jwt.JwtProvider;
import com.plantify.pay.repository.PayRepository;
import com.plantify.pay.service.point.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayInternalServiceImpl implements PayInternalService {

    private final PayRepository payRepository;
    private final TransactionServiceClient transactionServiceClient;
    private final DistributedLock distributedLock;
    private final PointService pointService;
    private final JwtProvider jwtProvider;

    @Override
    public Pay rechargeBalance(Long userId, Long amount) {
        String lockKey = String.format("pay:%d", userId);

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Pay pay = payRepository.findByUserId(userId)
                    .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

            pay.validateAmount(amount).updatedBalance(amount);

            return payRepository.save(pay);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    @Override
    public PaymentResponse payTransaction(TransactionRequest request, boolean isExternal) {
        String lockKey = String.format("pay:%d", request.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Pay pay = payRepository.findByUserId(request.userId())
                    .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

            validateTransactionStatus(request.userId(), request.orderId());

            if (request.pointToUse() > 0) {
                pointService.PointsToUse(request.userId(), request.pointToUse());
            }

            long finalAmount = request.amount() - request.pointToUse();
            pay.validatePay(finalAmount).success(finalAmount);
            payRepository.save(pay);

            return createPayTransaction(request);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    @Override
    public PaymentResponse createPayTransaction(TransactionRequest request) {
        PaymentRequest paymentRequest = new PaymentRequest(
                request.userId(),
                request.orderId(),
                request.orderName(),
                request.sellerId(),
                request.amount() - request.pointToUse()
        );

        TransactionResponse response = transactionServiceClient.createPayTransaction(paymentRequest).getData();
        String token = jwtProvider.createAccessToken(response.transactionId());

        return PaymentResponse.from(response, token);
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

    private void validateTransactionStatus(Long userId, Long orderId) {
        boolean existsPendingOrSuccessTransaction = transactionServiceClient.existsByUserIdAndStatusIn(
                userId, orderId, List.of(Status.PENDING, Status.SUCCESS)
        );
        if (existsPendingOrSuccessTransaction) {
            throw new ApplicationException(PayErrorCode.DUPLICATE_TRANSACTION);
        }
    }
}