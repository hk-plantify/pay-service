package com.plantify.pay.service.account;

import com.plantify.pay.domain.dto.account.AccountUserRequest;
import com.plantify.pay.domain.dto.account.AccountUserResponse;
import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.AccountStatus;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.AccountErrorCode;
import com.plantify.pay.repository.AccountRepository;
import com.plantify.pay.util.UserInfoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountUserServiceImpl implements AccountUserService {

    private final AccountRepository accountRepository;
    private final UserInfoProvider userInfoProvider;

    @Override
    public List<AccountUserResponse> getAllAccounts() {
        Long userId = userInfoProvider.getUserInfo().userId();
        return accountRepository.findByUserId(userId)
                .stream()
                .map(AccountUserResponse::from)
                .collect(Collectors.toList());
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

        Account updatedAccount = request.updatedAccount(account);
        accountRepository.save(updatedAccount);

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