package com.plantify.pay.controller.settlement;

import com.plantify.pay.domain.dto.settlement.PaySettlementUserResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.settlement.PaySettlementUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay/settlements")
public class PaySettlementUserController {

    private final PaySettlementUserService paySettlementUserService;

    // 자신의 모든 결제 내역
    @GetMapping
    public ApiResponse<List<PaySettlementUserResponse>> getAllPaySettlements() {
        List<PaySettlementUserResponse> response = paySettlementUserService.getAllPaySettlements();
        return ApiResponse.ok(response);
    }

    // 자신의 특정 결제 내역
    @GetMapping("/{paySettlementId}")
    public ApiResponse<PaySettlementUserResponse> getPaySettlementById(@PathVariable Long paySettlementId) {
        PaySettlementUserResponse response = paySettlementUserService.getPaySettlementById(paySettlementId);
        return ApiResponse.ok(response);
    }
}
