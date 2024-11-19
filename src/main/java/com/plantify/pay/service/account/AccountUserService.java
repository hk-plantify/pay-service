package com.plantify.pay.service.account;

import com.plantify.pay.domain.dto.request.AccountUserRequest;
import com.plantify.pay.domain.dto.response.AccountUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountUserService {

    Page<AccountUserResponse> getAllAccounts(Pageable pageable);
    AccountUserResponse getAccountByAccountId(Long accountId);
    AccountUserResponse createAccount(AccountUserRequest request);
    AccountUserResponse updateAccountByAccountId(Long accountId, AccountUserRequest request);
    void deactivateAccountByAccountId(Long accountId);
}