package com.plantify.pay.service.settlement;

import com.plantify.pay.domain.dto.settlement.PaySettlementUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaySettlementUserService {

    List<PaySettlementUserResponse> getAllPaySettlements();
    PaySettlementUserResponse getPaySettlementById(Long paySettlementId);
}