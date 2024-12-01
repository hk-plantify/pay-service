package com.plantify.pay.controller.account;

import com.plantify.pay.domain.dto.account.AccountAdminRequest;
import com.plantify.pay.domain.dto.account.AccountAdminResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.account.AccountAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/pay/accounts")
public class AccountAdminController {

    private final AccountAdminService accountAdminService;

    // 모든 계좌 조회
    @GetMapping
    public ApiResponse<List<AccountAdminResponse>> getAllAccounts() {
        List<AccountAdminResponse> response = accountAdminService.getAllAccounts();
        return ApiResponse.ok(response);
    }

    // 특정 사용자 계좌 조회
    @GetMapping("/users/{userId}")
    public ApiResponse<List<AccountAdminResponse>> getAccountsByUserId(@PathVariable Long userId) {
        List<AccountAdminResponse> response = accountAdminService.getAccountsByUserId(userId);
        return ApiResponse.ok(response);
    }

    // 특정 사용자 계좌 수정
    @PutMapping("/{accountId}/users/{userId}")
    public ApiResponse<AccountAdminResponse> updateAccountByAccountIdAndUserId(
            @PathVariable Long accountId, @PathVariable Long userId, @RequestBody AccountAdminRequest request) {
        AccountAdminResponse response = accountAdminService.updateAccountByAccountIdAndUserId(accountId, userId, request);
        return ApiResponse.ok(response);
    }

    // 특정 사용자 계좌 삭제(계좌 상태:INACTIVE)
    @DeleteMapping("/{accountId}/users/{userId}")
    public ApiResponse<AccountAdminResponse> deactivateAccountByAccountIdAndUserId(
            @PathVariable Long accountId, @PathVariable Long userId) {
        AccountAdminResponse response = accountAdminService.deactivateAccountByAccountIdAndUserId(accountId, userId);
        return ApiResponse.ok(response);
    }
}

