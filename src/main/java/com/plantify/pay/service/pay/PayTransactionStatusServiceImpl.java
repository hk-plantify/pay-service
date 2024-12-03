package com.plantify.pay.service.pay;

import com.plantify.pay.config.RedisLock;
import com.plantify.pay.domain.dto.kafka.TransactionStatusMessage;
import com.plantify.pay.domain.entity.*;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.repository.PayRepository;
import com.plantify.pay.repository.PaySettlementRepository;
import com.plantify.pay.service.point.PointService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PayTransactionStatusServiceImpl implements PayTransactionStatusService {

    private final PayRepository payRepository;
    private final PointService pointService;
    private final RedisLock redisLock;

    private static final int LOCK_TIMEOUT_MS = 3000;
    private static final double POINT_REWARD_RATE = 0.005;

    // 성공
    public void processSuccessfulTransaction(TransactionStatusMessage message) {
        String lockKey = String.format("transaction:%d", message.userId());

        try {
            if (!redisLock.tryLock(lockKey, LOCK_TIMEOUT_MS)) {
                throw new ApplicationException(PayErrorCode.CONCURRENT_UPDATE);
            }

            Pay pay = payRepository.findById(message.transactionId())
                    .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));
            if (message.amount() > 0) {
                pay = pay.toBuilder()
                        .balance(pay.getBalance() - message.amount())
                        .payStatus(Status.SUCCESS)
                        .build();
                payRepository.save(pay);

                Long rewardPoints = Math.round(message.amount() * POINT_REWARD_RATE);
                pointService.rewardPoints(message.userId(), rewardPoints);
            }
        } finally {
            redisLock.unlock(lockKey);
        }
    }

    // 실패
    public void processFailedTransaction(TransactionStatusMessage message) {
        String lockKey = String.format("transaction:%d", message.userId());

        try {
            if (!redisLock.tryLock(lockKey, LOCK_TIMEOUT_MS)) {
                throw new ApplicationException(PayErrorCode.CONCURRENT_UPDATE);
            }

            Pay pay = payRepository.findById(message.transactionId())
                    .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));
            pay = pay.toBuilder()
                    .payStatus(Status.FAILED)
                    .build();
            payRepository.save(pay);
        } finally {
            redisLock.unlock(lockKey);
        }
    }
}