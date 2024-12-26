package com.plantify.pay.service.account;

import com.plantify.pay.domain.dto.account.AccountUserRequest;
import com.plantify.pay.domain.dto.account.AccountUserResponse;
import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.AccountStatus;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.AccountErrorCode;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.repository.AccountRepository;
import com.plantify.pay.global.util.UserInfoProvider;
import com.plantify.pay.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountUserServiceImpl implements AccountUserService {

    private final AccountRepository accountRepository;
    private final PayRepository payRepository;
    private final UserInfoProvider userInfoProvider;

    @Override
    public List<AccountUserResponse> getAllAccounts() {
        Long userId = userInfoProvider.getUserInfo().userId();
        return accountRepository.findByPayUserId(userId)
                .stream()
                .map(AccountUserResponse::from)
                .toList();
    }

    @Override
    public AccountUserResponse getAccountByAccountId(Long accountId) {
        Long userId = userInfoProvider.getUserInfo().userId();
        Account account = accountRepository.findByAccountIdAndPayUserId(accountId, userId)
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));
        return AccountUserResponse.from(account);
    }

    @Override
    public AccountUserResponse createAccount(AccountUserRequest request) {
        if (accountRepository.existsByAccountNum(request.accountNum())) {
            throw new ApplicationException(AccountErrorCode.DUPLICATE_ACCOUNT);
        }

        Long userId = userInfoProvider.getUserInfo().userId();

        Pay pay = payRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

        Account account = request.toEntity().linkToPay(pay);;
        accountRepository.save(account);
        return AccountUserResponse.from(account);
    }

    @Override
    public void deleteAccount(Long accountId) {
        Long userId = userInfoProvider.getUserInfo().userId();

        long accountCount = accountRepository.countByPayUserId(userId);
        if (accountCount <= 1) {
            throw new ApplicationException(AccountErrorCode.LEAST_ONE_ACCOUNT);
        }

        Account account = accountRepository.findByAccountIdAndPayUserId(accountId, userId)
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));

        accountRepository.delete(account);
    }
}