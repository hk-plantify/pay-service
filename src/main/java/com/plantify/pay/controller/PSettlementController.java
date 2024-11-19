package com.plantify.pay.controller;

import com.plantify.pay.domain.dto.response.PSettlementResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.PSettlementService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay/settlements")
public class PSettlementController {

    private final PSettlementService settlementService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PSettlementResponse>>> getUserSettlements(Pageable pageable) {
        Page<PSettlementResponse> response = settlementService.getUserSettlements(pageable);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/{settlementId}")
    public ResponseEntity<ApiResponse<PSettlementResponse>> getSettlementById(@PathVariable Long settlementId) {
        PSettlementResponse response = settlementService.getSettlementById(settlementId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<Page<PSettlementResponse>>> getSettlementsByUserId(
            @PathVariable Long userId, Pageable pageable) {
        Page<PSettlementResponse> response = settlementService.getSettlementsByUserId(userId, pageable);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
