package com.plantify.pay.service;

import com.plantify.pay.domain.dto.request.AccountRequest;
import com.plantify.pay.domain.dto.response.AccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    Page<AccountResponse> getAllAccounts(Pageable pageable);
    AccountResponse getAccount(Long accountId);
    Page<AccountResponse> getAccountsByUserId(Long userId, Pageable pageable);
    AccountResponse createAccount(AccountRequest request);
    AccountResponse updateAccount(Long accountId, AccountRequest request);
    void deleteAccount(Long accountId);
    void deleteAccountByUserId(Long accountId, Long userId);
}
