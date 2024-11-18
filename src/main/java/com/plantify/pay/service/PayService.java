package com.plantify.pay.service;

import com.plantify.pay.domain.dto.request.PayRequest;
import com.plantify.pay.domain.dto.response.PayResponse;
import com.plantify.pay.domain.entity.PayStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PayService {

    Page<PayResponse> getAllPays(Pageable pageable);
    Page<PayResponse> getPaysByUserId(Long userId, Pageable pageable);
    PayResponse createPay(PayRequest request);
    PayResponse updatePay(Long payId, PayRequest request);
    PayResponse changePayStatus(Long payId, PayStatus status);

}
