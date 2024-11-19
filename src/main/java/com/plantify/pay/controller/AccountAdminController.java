package com.plantify.pay.controller;

import com.plantify.pay.domain.dto.request.AccountAdminRequest;
import com.plantify.pay.domain.dto.response.AccountAdminResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.admin.AccountAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/pay/accounts")
public class AccountAdminController {

    private final AccountAdminService accountAdminService;

    // 모든 계좌 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountAdminResponse>>> getAllAccounts() {
        List<AccountAdminResponse> response = accountAdminService.getAllAccounts();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 특정 사용자 계좌 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<AccountAdminResponse>>> getAccountsByUserId(
            @PathVariable Long userId) {
        List<AccountAdminResponse> response = accountAdminService.getAccountsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 특정 사용자 계좌 수정
    @PutMapping("/{accountId}/users/{userId}")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> updateAccountByAccountIdAndUserId(
            @PathVariable Long accountId, @PathVariable Long userId, @RequestBody AccountAdminRequest request) {
        AccountAdminResponse response = accountAdminService.updateAccountByAccountIdAndUserId(accountId, userId, request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 특정 사용자 계좌 삭제(계좌 상태:INACTIVE)
    @DeleteMapping("/{accountId}/users/{userId}")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> deactivateAccountByAccountIdAndUserId(
            @PathVariable Long accountId, @PathVariable Long userId) {
        AccountAdminResponse response = accountAdminService.deactivateAccountByAccountIdAndUserId(accountId, userId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}

