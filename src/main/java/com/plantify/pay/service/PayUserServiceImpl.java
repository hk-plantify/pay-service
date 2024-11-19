package com.plantify.pay.service;

import com.plantify.pay.client.UserInfoProvider;
import com.plantify.pay.domain.dto.request.PayUserRequest;
import com.plantify.pay.domain.dto.response.PayUserResponse;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayUserServiceImpl implements PayUserService {

    private final PayRepository payRepository;
    private final UserInfoProvider userInfoProvider;

    @Override
    public Page<PayUserResponse> getAllPays(Pageable pageable) {
        Long userId = userInfoProvider.getUserInfo().userId();
        return payRepository.findByAccountUserId(userId, pageable)
                .map(PayUserResponse::from);
    }

    @Override
    public PayUserResponse getPayById(Long payId) {
        Long userId = userInfoProvider.getUserInfo().userId();
        Pay pay = payRepository.findByPayIdAndAccountUserId(payId, userId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));
        return PayUserResponse.from(pay);
    }

    @Override
    public PayUserResponse createPay(PayUserRequest request) {
        Long userId = userInfoProvider.getUserInfo().userId();
        if (payRepository.existsByAccountUserId(userId)) {
            throw new ApplicationException(PayErrorCode.PAY_ALREADY_EXISTS);
        }

        Pay pay = request.toEntity(userId);
        payRepository.save(pay);
        return PayUserResponse.from(pay);
    }

    @Override
    public PayUserResponse balanceRechargePay(Long payId, PayUserRequest request) {
        Long userId = userInfoProvider.getUserInfo().userId();
        Pay pay = payRepository.findByPayIdAndAccountUserId(payId, userId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

        pay = pay.toBuilder()
                .balance(pay.getBalance() + request.balance())
                .build();

        payRepository.save(pay);
        return PayUserResponse.from(pay);
    }
}
