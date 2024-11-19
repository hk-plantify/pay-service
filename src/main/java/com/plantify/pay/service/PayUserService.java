package com.plantify.pay.service;

import com.plantify.pay.domain.dto.request.PayUserRequest;
import com.plantify.pay.domain.dto.response.PayUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PayUserService {

    Page<PayUserResponse> getAllPays(Pageable pageable);
    PayUserResponse getPayById(Long payId);
    PayUserResponse createPay(PayUserRequest request);
    PayUserResponse balanceRechargePay(Long payId, PayUserRequest request);
}
