package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.account.AccountUserRequest;
import com.plantify.pay.domain.dto.pay.PayUserRequest;
import com.plantify.pay.domain.dto.pay.PayUserResponse;
import com.plantify.pay.domain.entity.Pay;

public interface PayUserService {

    PayUserResponse getPay();
    PayUserResponse createPay(AccountUserRequest request);
    PayUserResponse balanceRechargePay(PayUserRequest request);
    Pay rechargeBalance(Long userId, Long amount);
}
