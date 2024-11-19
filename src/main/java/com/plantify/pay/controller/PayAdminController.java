package com.plantify.pay.controller;

import com.plantify.pay.domain.dto.request.PayAdminRequest;
import com.plantify.pay.domain.dto.response.PayAdminResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.admin.PayAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/pay")
public class PayAdminController {

    private final PayAdminService payAdminService;

    // 모든 페이 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<PayAdminResponse>>> getAllPays() {
        List<PayAdminResponse> response = payAdminService.getAllPays();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 특정 사용자 페이 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<PayAdminResponse>>> getPaysByUserId(@PathVariable Long userId) {
        List<PayAdminResponse> response = payAdminService.getPaysByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 특정 페이 상태 변경 (PENDING, SUCCESS, CANCELLED, FAILED)
    @PutMapping("/{payId}/users/{userId}")
    public ResponseEntity<ApiResponse<PayAdminResponse>> updatePayByPayIdAndUserId(
            @PathVariable Long payId, @PathVariable Long userId, @RequestBody PayAdminRequest request) {
        PayAdminResponse response = payAdminService.updatePayByPayIdAndUserId(payId, userId, request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}