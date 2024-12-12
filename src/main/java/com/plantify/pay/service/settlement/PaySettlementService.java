package com.plantify.pay.service.settlement;

import com.plantify.pay.domain.dto.pay.ExternalSettlementResponse;

public interface PaySettlementService {

    ExternalSettlementResponse getSettlementByOrderId(String orderId);
}
