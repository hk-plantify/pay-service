package com.plantify.pay.service;

import com.plantify.pay.domain.dto.request.AccountRequest;
import com.plantify.pay.domain.dto.response.AccountResponse;
import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.AccountErrorCode;
import com.plantify.pay.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AuthenticationService authenticationService;

    @Override
    public Page<AccountResponse> getAllAccounts(Pageable pageable) {
        if (authenticationService.validateAdminRole()) {
            return accountRepository.findAll(pageable)
                    .map(AccountResponse::from);
        }

        Long userId = authenticationService.getUserId();
        return accountRepository.findByUserId(userId, pageable)
                .map(AccountResponse::from);
    }

    @Override
    public AccountResponse getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));
        authenticationService.validateOwnership(account.getUserId());

        return AccountResponse.from(account);
    }

    @Override
    public Page<AccountResponse> getAccountsByUserId(Long userId, Pageable pageable) {
        authenticationService.validateAdminRole();
        return accountRepository.findByUserId(userId, pageable)
                .map(AccountResponse::from);
    }

    @Override
    public AccountResponse createAccount(AccountRequest request) {
        Long userId = authenticationService.getUserId();
        Account account = request.toEntity(userId);
        Account savedAccount = accountRepository.save(account);

        return AccountResponse.from(savedAccount);
    }

    @Override
    public AccountResponse updateAccount(Long accountId, AccountRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));
        authenticationService.validateOwnership(account.getUserId());

        Account updatedAccount = account.toBuilder()
                .accountNum(request.accountNum())
                .bankName(request.bankName())
                .accountStatus(request.accountStatus())
                .accountHolder(request.accountHolder())
                .build();

        updatedAccount = accountRepository.save(updatedAccount);

        return AccountResponse.from(updatedAccount);
    }

    @Override
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));
        authenticationService.validateOwnership(account.getUserId());

        accountRepository.delete(account);
    }

    @Override
    public void deleteAccountByUserId(Long accountId, Long userId) {
        authenticationService.validateAdminRole();
        Account account = accountRepository.findByAccountIdAndUserId(accountId, userId)
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));

        accountRepository.delete(account);
    }
}