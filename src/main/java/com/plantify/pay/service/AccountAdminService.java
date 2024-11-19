package com.plantify.pay.service;

import com.plantify.pay.domain.dto.request.AccountAdminRequest;
import com.plantify.pay.domain.dto.response.AccountAdminResponse;

import java.util.List;

public interface AccountAdminService {

    List<AccountAdminResponse> getAllAccounts();
    List<AccountAdminResponse> getAccountsByUserId(Long userId);
    AccountAdminResponse updateAccountByAccountIdAndUserId(Long accountId, Long userId, AccountAdminRequest request);
    AccountAdminResponse deactivateAccountByAccountIdAndUserId(Long accountId, Long userId);
}