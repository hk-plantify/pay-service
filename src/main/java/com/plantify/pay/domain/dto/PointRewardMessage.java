package com.plantify.pay.domain.dto;

public record PointRewardMessage(
        Long userId,
        Long rewardPoints
) {}