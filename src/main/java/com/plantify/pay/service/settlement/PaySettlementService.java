package com.plantify.pay.service.settlement;

import com.plantify.pay.domain.dto.pay.ExternalSettlementResponse;
import com.plantify.pay.domain.dto.settlement.PaySettlementRequest;
import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.domain.entity.Status;

public interface PaySettlementService {

    void savePaySettlement(PaySettlementRequest request);
    ExternalSettlementResponse getSettlementByOrderId(String orderId);
    PaySettlement updateSettlementStatus(String orderId, Status status);
}
