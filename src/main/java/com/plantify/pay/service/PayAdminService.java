package com.plantify.pay.service;

import com.plantify.pay.domain.dto.request.PayAdminRequest;
import com.plantify.pay.domain.dto.response.PayAdminResponse;

import java.util.List;

public interface PayAdminService {

    List<PayAdminResponse> getAllPays();
    List<PayAdminResponse> getPaysByUserId(Long userId);
    PayAdminResponse updatePayByPayIdAndUserId(Long payId, Long userId, PayAdminRequest request);

}
