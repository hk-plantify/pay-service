package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.pay.PayAdminResponse;

import java.util.List;

public interface PayAdminService {

    List<PayAdminResponse> getAllPays();
    List<PayAdminResponse> getPaysByUserId(Long userId);

}
