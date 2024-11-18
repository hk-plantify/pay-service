package com.plantify.pay.controller;

import com.plantify.pay.domain.dto.request.AccountRequest;
import com.plantify.pay.domain.dto.response.AccountResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AccountResponse>>> getAccounts(Pageable pageable) {
        Page<AccountResponse> response = accountService.getAllAccounts(pageable);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(@PathVariable Long accountId) {
        AccountResponse response = accountService.getAccount(accountId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<Page<AccountResponse>>> getAccountsByUserId(
            @PathVariable Long userId, Pageable pageable) {
        Page<AccountResponse> response = accountService.getAccountsByUserId(userId, pageable);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@RequestBody AccountRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(
            @PathVariable Long accountId, @RequestBody AccountRequest request) {
        AccountResponse response = accountService.updateAccount(accountId, request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @DeleteMapping("/{accountId}/users/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteAccountByUserId(
            @PathVariable Long accountId, @PathVariable Long userId) {
        accountService.deleteAccountByUserId(accountId, userId);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}