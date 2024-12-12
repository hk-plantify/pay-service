package com.plantify.pay.domain.dto.kafka;

public record PayBalanceRequest(Long userId, Long amount) {
}
