package com.plantify.pay.controller.pay;

import com.plantify.pay.domain.dto.kafka.PaymentResponse;
import com.plantify.pay.domain.dto.kafka.RefundResponse;
import com.plantify.pay.domain.dto.kafka.TransactionRequest;
import com.plantify.pay.domain.dto.kafka.TransactionStatusResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.pay.PayService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay")
public class PayController {

    private final PayService payService;

    // 페이 결제 요청
    @PostMapping("/payment")
    public void paymentPending(
            @RequestBody TransactionRequest request, HttpServletResponse response) throws IOException {
        PaymentResponse paymentResponse = payService.payment(request);
        String redirectUrl = String.format("https://plantify/payments?token=%s", paymentResponse.token());
        response.sendRedirect(redirectUrl);
    }

    // 트랜잭션 상태 검증
    @GetMapping("/payment/{token}")
    public ApiResponse<TransactionStatusResponse> getTransactionStatus(@PathVariable String token) {
        TransactionStatusResponse status = payService.getTransactionStatus(token);
        return ApiResponse.ok(status);
    }

    // 페이 환불 요청
    @PostMapping("/refund")
    public ApiResponse<RefundResponse> refund(@RequestBody TransactionRequest request) {
        RefundResponse response = payService.refund(request);
        return ApiResponse.ok(response);
    }
}
