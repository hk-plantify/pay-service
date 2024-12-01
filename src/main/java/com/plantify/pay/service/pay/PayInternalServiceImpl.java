package com.plantify.pay.service.pay;

import com.plantify.pay.client.TransactionServiceClient;
import com.plantify.pay.config.RedisLock;
import com.plantify.pay.domain.dto.kafka.TransactionRequest;
import com.plantify.pay.domain.dto.kafka.TransactionResponse;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.Status;
import com.plantify.pay.domain.entity.TransactionType;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.jwt.JwtProvider;
import com.plantify.pay.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayInternalServiceImpl implements PayInternalService {

    private final RedisLock redisLock;
    private final PayRepository payRepository;
    private final TransactionServiceClient transactionServiceClient;

    private static final int LOCK_TIMEOUT_MS = 3000;
    private static final int MINIMUM_CHARGE_UNIT = 10_000;

    @Override
    public Pay rechargeBalance(Long userId, Long amount) {
        String lockKey = String.format("pay:%d", userId);

        try {
            tryLockOrThrow(lockKey);
            validateAmount(amount);
            Pay pay = payRepository.findByAccountUserId(userId)
                    .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

            pay = pay.toBuilder()
                    .balance(pay.getBalance() + amount)
                    .build();
            return payRepository.save(pay);
        } finally {
            redisLock.unlock(lockKey);
        }
    }

    @Override
    public TransactionResponse executeTransaction(Long sellerId, TransactionRequest request, boolean isExternal) {
        Long userId = request.userId();
        String lockKey = String.format("transaction:%d", userId);

        try {
            tryLockOrThrow(lockKey);
            validateTransactionStatus(userId);
            validatePayBalance(userId, request.amount());

            return createTransaction(sellerId, request);
        } finally {
            redisLock.unlock(lockKey);
        }
    }

    @Override
    public TransactionResponse refundTransaction(Long sellerId, TransactionRequest request) {
        Long userId = request.userId();
        String lockKey = String.format("refund:%d", userId);

        try {
            tryLockOrThrow(lockKey);
            validateAmount(request.amount());
            Pay pay = payRepository.findByAccountUserId(userId)
                    .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

            pay = pay.toBuilder()
                    .balance(pay.getBalance() + request.amount())
                    .build();
            payRepository.save(pay);

            return createTransaction(sellerId, request);
        } finally {
            redisLock.unlock(lockKey);
        }
    }

    // 금액 검증 -> 충전/환불
    private void validateAmount(Long amount) {
        if (amount <= 0) {
            throw new ApplicationException(PayErrorCode.INVALID_PAY_INPUT);
        }
        if (amount % MINIMUM_CHARGE_UNIT != 0) {
            throw new ApplicationException(PayErrorCode.INVALID_CHARGE_UNIT);
        }
    }

    // 잔액 검증 -> 결제
    @Override
    public void validatePayBalance(Long userId, Long amount) {
        Pay pay = payRepository.findByAccountUserId(userId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));
        if (pay.getBalance() < amount) {
            throw new ApplicationException(PayErrorCode.INSUFFICIENT_BALANCE);
        }
    }

    // 트랜잭션 생성 -> 결제/환불
    @Override
    public TransactionResponse createTransaction(Long sellerId, TransactionRequest request) {
        Long userId = request.userId();
        TransactionRequest transactionRequest = new TransactionRequest(
                userId,
                sellerId,
                request.transactionType(),
                request.amount(),
                request.reason()
        );
        TransactionResponse transactionResponse = transactionServiceClient.createTransaction(transactionRequest);
        return TransactionResponse.from(transactionResponse);
    }

    // Redis 락 처리
    private void tryLockOrThrow(String lockKey) {
        if (!redisLock.tryLock(lockKey, LOCK_TIMEOUT_MS)) {
            throw new ApplicationException(PayErrorCode.CONCURRENT_UPDATE);
        }
    }

    private void validateTransactionStatus(Long userId) {
        boolean existsPendingOrSuccessTransaction = transactionServiceClient.existsByUserIdAndStatusIn(
                userId, List.of(Status.PENDING, Status.SUCCESS)
        );
        if (existsPendingOrSuccessTransaction) {
            throw new ApplicationException(PayErrorCode.DUPLICATE_TRANSACTION);
        }
    }
}