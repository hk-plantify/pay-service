package com.plantify.pay.service.settlement;

import com.plantify.pay.domain.dto.settlement.PaySettlementAdminResponse;

import java.util.List;

public interface PaySettlementAdminService {

    List<PaySettlementAdminResponse> getAllPaySettlements();
    PaySettlementAdminResponse getPaySettlementById(Long paySettlementId);
    List<PaySettlementAdminResponse> getPaySettlementsByUserId(Long userId);
}