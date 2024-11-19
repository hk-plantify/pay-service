package com.plantify.pay.service;

import com.plantify.pay.client.UserInfoProvider;
import com.plantify.pay.domain.dto.request.AccountUserRequest;
import com.plantify.pay.domain.dto.response.AccountUserResponse;
import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.AccountStatus;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.AccountErrorCode;
import com.plantify.pay.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountUserServiceImpl implements AccountUserService {

    private final AccountRepository accountRepository;
    private final UserInfoProvider userInfoProvider;

    @Override
    public Page<AccountUserResponse> getAllAccounts(Pageable pageable) {
        Long userId = userInfoProvider.getUserInfo().userId();
        return accountRepository.findByUserId(userId, pageable)
                .map(AccountUserResponse::from);
    }

    @Override
    public AccountUserResponse getAccountByAccountId(Long accountId) {
        Long userId = userInfoProvider.getUserInfo().userId();
        Account account = accountRepository.findByAccountIdAndUserId(accountId, userId)
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));
        return AccountUserResponse.from(account);
    }

    @Override
    public AccountUserResponse createAccount(AccountUserRequest request) {
        Long userId = userInfoProvider.getUserInfo().userId();
        Account account = request.toEntity(userId);
        accountRepository.save(account);
        return AccountUserResponse.from(account);
    }

    @Override
    public AccountUserResponse updateAccountByAccountId(Long accountId, AccountUserRequest request) {
        Long userId = userInfoProvider.getUserInfo().userId();
        Account account = accountRepository.findByAccountIdAndUserId(accountId, userId)
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));

        account = account.toBuilder()
                .accountNum(request.accountNum())
                .bankName(request.bankName())
                .accountStatus(request.accountStatus())
                .accountHolder(request.accountHolder())
                .build();

        accountRepository.save(account);

        return AccountUserResponse.from(account);
    }

    @Override
    public void deactivateAccountByAccountId(Long accountId) {
        Long userId = userInfoProvider.getUserInfo().userId();
        Account account = accountRepository.findByAccountIdAndUserId(accountId, userId)
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));

        account = account.toBuilder()
                .accountStatus(AccountStatus.INACTIVE)
                .build();

        accountRepository.save(account);
    }
}