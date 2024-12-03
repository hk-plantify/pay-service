package com.plantify.pay.controller.settlement;

import com.plantify.pay.domain.dto.settlement.PaySettlementAdminResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.settlement.PaySettlementAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/pay/settlements")
public class PaySettlementAdminController {

    private final PaySettlementAdminService paySettlementAdminService;

    // 모든 결제 정산 내역 조회
    @GetMapping
    public ApiResponse<List<PaySettlementAdminResponse>> getAllPaySettlements() {
        List<PaySettlementAdminResponse> response = paySettlementAdminService.getAllPaySettlements();
        return ApiResponse.ok(response);
    }

    // 특정 결제 정산 내역 조회
    @GetMapping("/{paySettlementId}")
    public ApiResponse<PaySettlementAdminResponse> getPaySettlementById(@PathVariable Long paySettlementId) {
        PaySettlementAdminResponse response = paySettlementAdminService.getPaySettlementById(paySettlementId);
        return ApiResponse.ok(response);
    }

    // 특정 사용자의 결제 정산 내역 조회
    @GetMapping("/users/{userId}")
    public ApiResponse<List<PaySettlementAdminResponse>> getPaySettlementsByUserId(@PathVariable Long userId) {
        List<PaySettlementAdminResponse> response = paySettlementAdminService.getPaySettlementsByUserId(userId);
        return ApiResponse.ok(response);
    }
}
