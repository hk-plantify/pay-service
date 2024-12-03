package com.plantify.pay.controller.pay;

import com.plantify.pay.domain.dto.kafka.ExternalTransactionResponse;
import com.plantify.pay.domain.dto.kafka.TransactionRequest;
import com.plantify.pay.domain.dto.kafka.TransactionResponse;
import com.plantify.pay.domain.dto.kafka.TransactionStatusResponse;
import com.plantify.pay.domain.dto.pay.PayUserRequest;
import com.plantify.pay.domain.dto.pay.PayUserResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.pay.PayTransactionService;
import com.plantify.pay.service.pay.PayUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay")
public class PayUserController {

    private final PayUserService payUserService;
    private final PayTransactionService payTransactionService;

    // 자신의 페이 조회(사용자 당 1개)
    @GetMapping
    public ApiResponse<PayUserResponse> getPay() {
        PayUserResponse response = payUserService.getPay();
        return ApiResponse.ok(response);
    }

    // 페이 생성(사용자 당 1개)
    @PostMapping
    public ApiResponse<PayUserResponse> createPay(@RequestBody PayUserRequest request) {
        PayUserResponse response = payUserService.createPay(request);
        return ApiResponse.ok(response);
    }

    // 페이 수정(잔액 충전)
    @PutMapping("/recharge")
    public ApiResponse<PayUserResponse> balanceRechargePay(@RequestBody PayUserRequest request) {
        PayUserResponse response = payUserService.balanceRechargePay(request);
        return ApiResponse.ok(response);
    }

    // 페이 내부 결제 요청
    @PostMapping("/payment/{sellerId}")
    public ApiResponse<TransactionResponse> createTransaction(
            @PathVariable Long sellerId, @RequestBody TransactionRequest request) {
        TransactionResponse response = payTransactionService.createTransaction(sellerId, request);
        return ApiResponse.ok(response);
    }

    // 페이 외부 결제 요청
    @PostMapping("/external/payment/{sellerId}")
    public ApiResponse<ExternalTransactionResponse> createExternalTransaction(
            @PathVariable Long sellerId, @RequestBody TransactionRequest request) {
        ExternalTransactionResponse response = payTransactionService.createExternalTransaction(sellerId, request);
        return ApiResponse.ok(response);
    }

    // 페이 토큰 검증
    @GetMapping("/external/payment/{token}")
    public ApiResponse<TransactionStatusResponse> getTransactionStatus(@PathVariable String token) {
        TransactionStatusResponse status = payTransactionService.getTransactionStatus(token);
        return ApiResponse.ok(status);
    }

    // 페이 환불 요청
    @PostMapping("/refund/{sellerId}")
    public ApiResponse<TransactionResponse> refundTransaction(
            @PathVariable Long sellerId, @RequestBody TransactionRequest request) {
        TransactionResponse response = payTransactionService.refundTransaction(sellerId, request);
        return ApiResponse.ok(response);
    }
}
