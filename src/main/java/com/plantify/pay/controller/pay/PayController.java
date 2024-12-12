package com.plantify.pay.controller.pay;

import com.plantify.pay.domain.dto.kafka.*;
import com.plantify.pay.domain.dto.pay.PayBalanceResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.pay.PayService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay")
public class PayController {

    private final PayService payService;

    @Value("${client.pay.url}")
    private String clientPayUrl;

    // 페이 결제 요청(Pending)
    @PostMapping("/payment")
    public void initiatePayment(
            @RequestBody TransactionRequest request, HttpServletResponse response) throws IOException {
        PaymentResponse paymentResponse = payService.initiatePayment(request);
        String redirectUrl = String.format("%s/?payments=%s", clientPayUrl, paymentResponse.token());
        log.info("{}", paymentResponse.token());
        response.sendRedirect(redirectUrl);
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

    // 페이 환불 요청
    @PostMapping("/refund")
    public ApiResponse<RefundResponse> refund(@RequestBody TransactionRequest request) {
        RefundResponse response = payService.refund(request);
        return ApiResponse.ok(response);
    }

    // 페이 잔액과 금액 비교
    @PostMapping("/check")
    public ApiResponse<PayBalanceResponse> checkPayBalance(@RequestBody PayBalanceRequest request) {
        PayBalanceResponse response = payService.checkPayBalance(request);
        return ApiResponse.ok(response);
    }
}
