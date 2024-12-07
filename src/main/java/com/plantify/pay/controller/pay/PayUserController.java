package com.plantify.pay.controller.pay;

import com.plantify.pay.domain.dto.account.AccountUserRequest;
import com.plantify.pay.domain.dto.kafka.PaymentResponse;
import com.plantify.pay.domain.dto.kafka.TransactionRequest;
import com.plantify.pay.domain.dto.kafka.RefundResponse;
import com.plantify.pay.domain.dto.kafka.TransactionStatusResponse;
import com.plantify.pay.domain.dto.pay.PayUserRequest;
import com.plantify.pay.domain.dto.pay.PayUserResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.pay.PayUserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay")
public class PayUserController {

    private final PayUserService payUserService;

    // 자신의 페이 조회(사용자 당 1개)
    @GetMapping
    public ApiResponse<PayUserResponse> getPay() {
        PayUserResponse response = payUserService.getPay();
        return ApiResponse.ok(response);
    }

    // 페이 생성(사용자 당 1개)
    @PostMapping
    public ApiResponse<PayUserResponse> createPay(@RequestBody AccountUserRequest request) {
        PayUserResponse response = payUserService.createPay(request);
        return ApiResponse.ok(response);
    }

    // 페이 수정(잔액 충전)
    @PutMapping("/recharge")
    public ApiResponse<PayUserResponse> balanceRechargePay(@RequestBody PayUserRequest request) {
        PayUserResponse response = payUserService.balanceRechargePay(request);
        return ApiResponse.ok(response);
    }
}
