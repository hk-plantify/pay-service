package com.plantify.pay.controller.account;

import com.plantify.pay.domain.dto.account.AccountUserRequest;
import com.plantify.pay.domain.dto.account.AccountUserResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.account.AccountUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay/accounts")
public class AccountUserController {

    private final AccountUserService accountUserService;

    // 자신의 모든 계좌 조회
    @GetMapping
    public ApiResponse<List<AccountUserResponse>> getAllAccounts() {
        List<AccountUserResponse> response = accountUserService.getAllAccounts();
        return ApiResponse.ok(response);
    }

    // 자신의 특정 계좌 조회
    @GetMapping("/{accountId}")
    public ApiResponse<AccountUserResponse> getAccountByAccountId(@PathVariable Long accountId) {
        AccountUserResponse response = accountUserService.getAccountByAccountId(accountId);
        return ApiResponse.ok(response);
    }

    // 계좌 연결(등록)
    @PostMapping
    public ApiResponse<AccountUserResponse> createAccount(@RequestBody AccountUserRequest request) {
        AccountUserResponse response = accountUserService.createAccount(request);
        return ApiResponse.ok(response);
    }

    // 자신의 특정 계좌 수정
    @PutMapping("/{accountId}")
    public ApiResponse<AccountUserResponse> updateAccountByAccountId(
            @PathVariable Long accountId, @RequestBody AccountUserRequest request) {
        AccountUserResponse response = accountUserService.updateAccountByAccountId(accountId, request);
        return ApiResponse.ok(response);
    }

    // 자신의 계좌 삭제(계좌 상태:INACTIVE)
    @DeleteMapping("/{accountId}")
    public ApiResponse<Void> deactivateAccountByAccountId(@PathVariable Long accountId) {
        accountUserService.deactivateAccountByAccountId(accountId);
        return ApiResponse.ok();
    }
}
