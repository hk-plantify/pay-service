package com.plantify.pay.service;

import com.plantify.pay.domain.dto.response.PaySettlementUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaySettlementUserService {

    Page<PaySettlementUserResponse> getAllPaySettlements(Pageable pageable);
    PaySettlementUserResponse getPaySettlementById(Long paySettlementId);
}