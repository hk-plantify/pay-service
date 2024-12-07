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

    // 특정 사용자 계좌 수정(status 변경)
    @PutMapping("/{accountId}/users")
    public ApiResponse<AccountAdminResponse> updateAccountStatus(
            @PathVariable Long accountId, @RequestBody AccountAdminRequest request) {
        AccountAdminResponse response = accountAdminService.updateAccountByAccountIdAndUserId(accountId, request);
        return ApiResponse.ok(response);
    }

    // 특정 사용자 계좌 삭제
    @DeleteMapping("/{accountId}/users")
    public ApiResponse<Void> deleteAccountByAccountIdAndUserId(
            @PathVariable Long accountId, @PathVariable Long userId) {
        accountAdminService.deleteAccountByAccountIdAndUserId(accountId, userId);
        return ApiResponse.ok();
    }
}

