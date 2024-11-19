package com.plantify.pay.kafka;

import com.plantify.pay.domain.dto.PointRewardMessage;
import com.plantify.pay.domain.dto.TransactionStatusMessage;
import com.plantify.pay.service.PointUserService;
import com.plantify.pay.service.PayUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final PayUserService payUserService;
    private final PointUserService pointUserService;

    @KafkaListener(topics = "transaction-status", groupId = "pay-service-group")
    public void handleTransactionStatus(TransactionStatusMessage message) {
        if ("SUCCESS".equals(message.getStatus())) {
            payUserService.handleTransactionSuccess(message.getTransactionId(), message.getUserId(), message.getAmount());
        } else if ("FAILED".equals(message.getStatus())) {
            payUserService.handleTransactionFailure(message.getTransactionId(), message.getUserId());
        }
    }

    @KafkaListener(topics = "point-reward", groupId = "pay-service-group")
    public void handlePointReward(PointRewardMessage message) {
        pointUserService.rewardPoints(message.getUserId(), message.getRewardPoints());
    }
}