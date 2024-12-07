package com.plantify.pay.service.account;

import com.plantify.pay.domain.dto.account.AccountAdminRequest;
import com.plantify.pay.domain.dto.account.AccountAdminResponse;
import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.AccountStatus;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.AccountErrorCode;
import com.plantify.pay.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountAdminServiceImpl implements AccountAdminService {

    private final AccountRepository accountRepository;

    @Override
    public List<AccountAdminResponse> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(AccountAdminResponse::from)
                .toList();
    }

    @Override
    public List<AccountAdminResponse> getAccountsByUserId(Long userId) {
        return accountRepository.findByPayUserId(userId).stream()
                .map(AccountAdminResponse::from)
                .toList();
    }

    @Override
    public AccountAdminResponse updateAccountByAccountIdAndUserId(Long accountId, AccountAdminRequest request) {
        Account account = accountRepository.findByAccountIdAndPayUserId(accountId, request.userId())
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));

        account.updateStatus(request.accountStatus());
        accountRepository.save(account);
        return AccountAdminResponse.from(account);
    }

    @Override
    public void deleteAccountByAccountIdAndUserId(Long accountId, Long userId) {
        Account account = accountRepository.findByAccountIdAndPayUserId(accountId, userId)
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));

        accountRepository.delete(account);
    }
}