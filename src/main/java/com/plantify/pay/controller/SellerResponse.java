package com.plantify.pay.controller;

import java.time.LocalDateTime;

public record SellerResponse(
        Long sellerId,
        String name,
        String contactInfo,
        String businessInfo,
        String status,
        String redirectUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
