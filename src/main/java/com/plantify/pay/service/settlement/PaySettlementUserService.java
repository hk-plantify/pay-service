package com.plantify.pay.service.settlement;

import com.plantify.pay.domain.dto.settlement.PaySettlementUserResponse;
import com.plantify.pay.domain.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PaySettlementUserService {

    Page<PaySettlementUserResponse> getAllPaySettlements(Pageable pageable);
    PaySettlementUserResponse getPaySettlementByStatus(Status status);
}