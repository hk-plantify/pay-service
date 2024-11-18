package com.plantify.pay.controller;

import com.plantify.pay.domain.dto.request.PayRequest;
import com.plantify.pay.domain.dto.response.PayResponse;
import com.plantify.pay.domain.entity.PayStatus;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay")
public class PayController {

    private final PayService payService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PayResponse>>> getPays(Pageable pageable) {
        Page<PayResponse> response = payService.getAllPays(pageable);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<Page<PayResponse>>> getPaysByUserId(
            @PathVariable Long userId, Pageable pageable) {
        Page<PayResponse> response = payService.getPaysByUserId(userId, pageable);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PayResponse>> createPay(@RequestBody PayRequest request) {
        PayResponse response = payService.createPay(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<PayResponse>> updatePay(@RequestBody PayRequest request) {
        PayResponse response = payService.updatePay(request.accountId(), request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<PayResponse>> changePayStatus(
            @RequestParam Long payId, @RequestParam PayStatus status) {
        PayResponse response = payService.changePayStatus(payId, status);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

}
