package com.plantify.pay.controller;

import com.plantify.pay.domain.dto.request.AccountUserRequest;
import com.plantify.pay.domain.dto.response.AccountAdminResponse;
import com.plantify.pay.domain.dto.response.AccountUserResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.AccountUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay/accounts")
public class AccountUserController {

    private final AccountUserService accountUserService;

    // 자신의 모든 계좌 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AccountUserResponse>>> getAllAccounts(Pageable pageable) {
        Page<AccountUserResponse> response = accountUserService.getAllAccounts(pageable);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 자신의 특정 계좌 조회
    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountUserResponse>> getAccountByAccountId(
            @PathVariable Long accountId) {
        AccountUserResponse response = accountUserService.getAccountByAccountId(accountId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 계좌 연결(등록)
    @PostMapping
    public ResponseEntity<ApiResponse<AccountUserResponse>> createAccount(
            @RequestBody AccountUserRequest request) {
        AccountUserResponse response = accountUserService.createAccount(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 자신의 특정 계좌 수정
    @PutMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountUserResponse>> updateAccountByAccountId(
            @PathVariable Long accountId) {
        AccountUserResponse response = accountUserService.updateAccountByAccountId(accountId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 자신의 계좌 삭제(계좌 상태:INACTIVE)
    @DeleteMapping("/{accountId}")
    public ResponseEntity<ApiResponse<Void>> deactivateAccountByAccountId(
            @PathVariable Long accountId) {
        accountUserService.deactivateAccountByAccountId(accountId);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
