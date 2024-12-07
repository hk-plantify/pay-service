package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.kafka.TransactionStatusMessage;
import com.plantify.pay.domain.entity.*;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.global.util.DistributedLock;
import com.plantify.pay.repository.PayRepository;
import com.plantify.pay.service.point.PointService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PayTransactionStatusServiceImpl implements PayTransactionStatusService {

    private final PayRepository payRepository;
    private final PointService pointService;
    private final DistributedLock distributedLock;

    private static final double POINT_REWARD_RATE = 0.005;

    // 성공
    public void processSuccessfulTransaction(TransactionStatusMessage message) {
        log.info("Received TransactionStatusMessage: {}", message);
        String lockKey = String.format("pay:%d", message.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Pay pay = payRepository.findByUserId(message.userId())
                    .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));
            pay.success(message.amount());
            payRepository.save(pay);

            Long rewardPoints = Math.round(message.amount() * POINT_REWARD_RATE);
            pointService.rewardPoints(message.userId(), rewardPoints);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    // 실패
    public void processFailedTransaction(TransactionStatusMessage message) {
        String lockKey = String.format("pay:%d", message.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Pay pay = payRepository.findByUserId(message.userId())
                    .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND))
                    .fail();

            payRepository.save(pay);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }
}