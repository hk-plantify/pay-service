package com.plantify.pay.controller.pay;

import com.plantify.pay.domain.dto.process.*;
import com.plantify.pay.domain.dto.pay.PayBalanceResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.pay.PayService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay")
public class PayController {

    private final PayService payService;

    // 페이 결제 요청(Pending)
    @PostMapping("/payment")
    public String initiatePayment(@RequestBody PendingTransactionRequest request)  {
        PaymentResponse paymentResponse = payService.createPayTransaction(request);
        return paymentResponse.token();
    }

    // 트랜잭션 상태 검증
    @GetMapping("/payment/verify")
    public ApiResponse<TransactionStatusResponse> getTransactionStatus(
            @RequestHeader String token) {
        TransactionStatusResponse status = payService.getTransactionStatus(token);
        return ApiResponse.ok(status);
    }

    // 토큰 검증 및 결제 요청
    @GetMapping("/payment")
    public ApiResponse<ProcessPaymentResponse> verifyAndProcessPayment(@RequestHeader String token, @RequestParam Long pointToUse) {
        ProcessPaymentResponse status = payService.verifyAndProcessPayment(token, pointToUse);
        return ApiResponse.ok(status);
    }

    // 결제 환불 요청
    @PostMapping("/refund")
    public ApiResponse<ProcessPaymentResponse> refund(@RequestBody UpdateTransactionRequest request) {
        ProcessPaymentResponse response = payService.refund(request);
        return ApiResponse.ok(response);
    }

    // 결제 취소 요청
    @PostMapping("/cancellation")
    public ApiResponse<ProcessPaymentResponse> cancellation(@RequestBody UpdateTransactionRequest request) {
        ProcessPaymentResponse response = payService.cancellation(request);
        return ApiResponse.ok(response);
    }

    // 페이 잔액과 금액 비교
    @PostMapping("/check")
    public ApiResponse<PayBalanceResponse> checkPayBalance(@RequestBody PayBalanceRequest request) {
        PayBalanceResponse response = payService.checkPayBalance(request);
        return ApiResponse.ok(response);
    }
}
