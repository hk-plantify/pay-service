package com.plantify.pay.service.account;

import com.plantify.pay.domain.dto.account.AccountUserRequest;
import com.plantify.pay.domain.dto.account.AccountUserResponse;

import java.util.List;

public interface AccountUserService {

    List<AccountUserResponse> getAllAccounts();
    AccountUserResponse getAccountByAccountId(Long accountId);
    AccountUserResponse createAccount(AccountUserRequest request);
    void deleteAccount(Long accountId);
}