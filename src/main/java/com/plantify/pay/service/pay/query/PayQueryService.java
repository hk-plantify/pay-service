package com.plantify.pay.service.pay.query;

import com.plantify.pay.domain.dto.pay.PayBalanceResponse;
import com.plantify.pay.domain.dto.process.PayBalanceRequest;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.global.util.UserInfoProvider;
import com.plantify.pay.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayQueryService {

    private final PayRepository payRepository;
    private final UserInfoProvider userInfoProvider;

    @Transactional(readOnly = true)
    public PayBalanceResponse checkBalance(PayBalanceRequest request) {

        Long userId = userInfoProvider.getUserInfo().userId();

        Pay pay = payRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

        if (request.amount() > pay.getBalance()) {
            throw new ApplicationException(PayErrorCode.INSUFFICIENT_BALANCE);
        }

        return new PayBalanceResponse(pay.getBalance());
    }
}
