package com.plantify.pay.kafka;

import com.plantify.pay.domain.dto.kafka.TransactionStatusMessage;
import com.plantify.pay.service.pay.PayTransactionStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PayConsumer {

    private final PayTransactionStatusService payTransactionStatusService;

    private static final String SUCCESS_STATUS = "SUCCESS";
    private static final String FAILED_STATUS = "FAILED";

    @KafkaListener(
            topics = "${spring.kafka.topic.transaction-status}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleTransactionStatus(TransactionStatusMessage message) {
        try {
            switch (message.status()) {
                case SUCCESS_STATUS -> payTransactionStatusService.processSuccessfulTransaction(message);
                case FAILED_STATUS -> payTransactionStatusService.processFailedTransaction(message);
                default -> log.warn("Unknown status: {}", message.status());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

