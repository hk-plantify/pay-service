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
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountAdminResponse> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId).stream()
                .map(AccountAdminResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public AccountAdminResponse updateAccountByAccountIdAndUserId(Long accountId, Long userId, AccountAdminRequest request) {
        Account account = accountRepository.findByAccountIdAndUserId(accountId, userId)
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));

        account = account.toBuilder()
                .accountNum(request.accountNum())
                .bankName(request.bankName())
                .accountStatus(request.accountStatus())
                .accountHolder(request.accountHolder())
                .build();

        accountRepository.save(account);

        return AccountAdminResponse.from(account);
    }

    @Override
    public AccountAdminResponse deactivateAccountByAccountIdAndUserId(Long accountId, Long userId) {
        Account account = accountRepository.findByAccountIdAndUserId(accountId, userId)
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));

        account = account.toBuilder()
                .accountStatus(AccountStatus.INACTIVE)
                .build();

        accountRepository.save(account);

        return AccountAdminResponse.from(account);
    }
}