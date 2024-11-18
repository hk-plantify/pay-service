package com.plantify.pay.service;

import com.plantify.pay.domain.dto.request.PayRequest;
import com.plantify.pay.domain.dto.response.PayResponse;
import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.PayStatus;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.AccountErrorCode;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.repository.AccountRepository;
import com.plantify.pay.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private final PayRepository payRepository;
    private final AccountRepository accountRepository;
    private final AuthenticationService authenticationService;

    @Override
    public Page<PayResponse> getAllPays(Pageable pageable) {
        if (authenticationService.validateAdminRole()) {
            return payRepository.findAll(pageable).map(PayResponse::from);
        }

        Long userId = authenticationService.getUserId();
        return payRepository.findByAccount_UserId(userId, pageable).map(PayResponse::from);
    }

    @Override
    public Page<PayResponse> getPaysByUserId(Long userId, Pageable pageable) {
        authenticationService.validateAdminRole();
        return payRepository.findByAccount_UserId(userId, pageable).map(PayResponse::from);
    }

    @Override
    public PayResponse createPay(PayRequest request) {
        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));

        authenticationService.validateOwnership(account.getUserId());

        Pay pay = request.toEntity(account);
        Pay savedPay = payRepository.save(pay);
        return PayResponse.from(savedPay);
    }

    @Override
    public PayResponse updatePay(Long payId, PayRequest request) {
        Pay pay = payRepository.findById(payId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAYMENT_NOT_FOUND));

        authenticationService.validateOwnership(pay.getAccount().getUserId());

        Pay updatedPay = pay.toBuilder()
                .balance(request.balance())
                .build();

        updatedPay = payRepository.save(updatedPay);
        return PayResponse.from(updatedPay);
    }

    @Override
    public PayResponse changePayStatus(Long payId, PayStatus status) {
        Pay pay = payRepository.findById(payId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAYMENT_NOT_FOUND));

        authenticationService.validateAdminRole();

        Pay updatedPay = pay.toBuilder()
                .payStatus(status)
                .build();

        updatedPay = payRepository.save(updatedPay);
        return PayResponse.from(updatedPay);
    }
}