package com.plantify.pay.controller;

import com.plantify.pay.domain.dto.request.PayUserRequest;
import com.plantify.pay.domain.dto.response.PayUserResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.PayUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay")
public class PayUserController {

    private final PayUserService payUserService;

    // 자신의 모든 페이 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PayUserResponse>>> getAllPays(Pageable pageable) {
        Page<PayUserResponse> response = payUserService.getAllPays(pageable);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 페이 생성(사용자 당 1개)
    @PostMapping
    public ResponseEntity<ApiResponse<PayUserResponse>> createPay(@RequestBody PayUserRequest request) {
        PayUserResponse response = payUserService.createPay(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 페이 수정(잔액 충전)
    @PutMapping("/{payId}")
    public ResponseEntity<ApiResponse<PayUserResponse>> balanceRechargePay(@PathVariable Long payId, @RequestBody PayUserRequest request) {
        PayUserResponse response = payUserService.balanceRechargePay(payId, request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
