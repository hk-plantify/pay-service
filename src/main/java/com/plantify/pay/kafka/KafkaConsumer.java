package com.plantify.pay.kafka;

import com.plantify.pay.domain.dto.PointRewardMessage;
import com.plantify.pay.domain.dto.TransactionStatusMessage;
import com.plantify.pay.service.pay.PayInternalService;
import com.plantify.pay.service.point.PointUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final PointUserService pointUserService;
    private final PayInternalService payInternalService;

    @KafkaListener(topics = "transaction-status", groupId = "pay-service-group")
    public void handleTransactionStatus(TransactionStatusMessage message) {
        if ("SUCCESS".equals(message.status())) {
            payInternalService.handleTransactionSuccess(message.transactionId(), message.userId(), message.amount());
        } else if ("FAILED".equals(message.status())) {
            payInternalService.handleTransactionFailure(message.transactionId(), message.userId());
        }
    }

    @KafkaListener(topics = "point-reward", groupId = "pay-service-group")
    public void handlePointReward(PointRewardMessage message) {
        pointUserService.rewardPoints(message.userId(), message.rewardPoints());
    }
}