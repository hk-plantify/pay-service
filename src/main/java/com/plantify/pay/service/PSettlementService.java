package com.plantify.pay.service;

import com.plantify.pay.domain.dto.response.PSettlementResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PSettlementService {

    Page<PSettlementResponse> getUserSettlements(Pageable pageable);
    PSettlementResponse getSettlementById(Long settlementId);
    Page<PSettlementResponse> getSettlementsByUserId(Long userId, Pageable pageable);
}