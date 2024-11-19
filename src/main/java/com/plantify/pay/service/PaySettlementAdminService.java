package com.plantify.pay.service;

import com.plantify.pay.domain.dto.response.PaySettlementAdminResponse;

import java.util.List;

public interface PaySettlementAdminService {

    List<PaySettlementAdminResponse> getAllPaySettlements();
    PaySettlementAdminResponse getPaySettlementById(Long paySettlementId);
    List<PaySettlementAdminResponse> getPaySettlementsByUserId(Long userId);
}