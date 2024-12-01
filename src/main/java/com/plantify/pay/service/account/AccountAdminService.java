package com.plantify.pay.service.account;

import com.plantify.pay.domain.dto.account.AccountAdminRequest;
import com.plantify.pay.domain.dto.account.AccountAdminResponse;

import java.util.List;

public interface AccountAdminService {

    List<AccountAdminResponse> getAllAccounts();
    List<AccountAdminResponse> getAccountsByUserId(Long userId);
    AccountAdminResponse updateAccountByAccountIdAndUserId(Long accountId, Long userId, AccountAdminRequest request);
    AccountAdminResponse deactivateAccountByAccountIdAndUserId(Long accountId, Long userId);
}