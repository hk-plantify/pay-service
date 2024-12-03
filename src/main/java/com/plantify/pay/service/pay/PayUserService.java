package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.pay.PayUserRequest;
import com.plantify.pay.domain.dto.pay.PayUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PayUserService {

    PayUserResponse getPay();
    PayUserResponse createPay(PayUserRequest request);
    PayUserResponse balanceRechargePay(PayUserRequest request);
}
