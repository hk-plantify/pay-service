package com.plantify.pay.client;

import com.plantify.pay.domain.dto.kafka.TransactionRequest;
import com.plantify.pay.domain.dto.kafka.TransactionResponse;
import com.plantify.pay.domain.entity.Status;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "transaction-service", url = "${transaction.service.url}")
public interface TransactionServiceClient {

    @PostMapping("v1/transactions")
    TransactionResponse createTransaction(@RequestBody TransactionRequest request);

    @GetMapping("v1/transactions/{transactionId}")
    TransactionResponse getTransactionById(@PathVariable Long transactionId);

    @GetMapping("v1/transactions/exist")
    boolean existsByUserIdAndStatusIn(@RequestParam Long userId, @RequestParam List<Status> statusList);
}