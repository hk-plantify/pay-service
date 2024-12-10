package com.plantify.pay.client;

import com.plantify.pay.domain.dto.kafka.*;
import com.plantify.pay.domain.entity.Status;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.domain.dto.kafka.PayTransactionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "transaction-service", url = "${transaction.service.url}")
public interface TransactionServiceClient {

    @GetMapping("/v1/transactions/{transactionId}")
    ApiResponse<TransactionResponse> getTransactionById(@PathVariable Long transactionId);

    @PostMapping("/v1/transactions")
    ApiResponse<TransactionResponse> createPendingTransaction(@RequestBody PaymentRequest request);

    @PostMapping("/v1/transactions/payments")
    ApiResponse<TransactionResponse> createPayTransaction(@RequestBody PayTransactionRequest request);

    @GetMapping("/v1/transactions/exist")
    boolean existsByUserIdAndStatusIn(@RequestParam Long userId, @RequestParam String orderId, @RequestParam List<Status> statusList);

    @PostMapping("/v1/transactions/refunds")
    ApiResponse<TransactionResponse> refundTransaction(@RequestBody TransactionRequest request);
}