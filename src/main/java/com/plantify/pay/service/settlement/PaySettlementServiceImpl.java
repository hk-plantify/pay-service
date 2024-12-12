package com.plantify.pay.service.settlement;

import com.plantify.pay.domain.dto.pay.ExternalSettlementResponse;
import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.SettlementErrorCode;
import com.plantify.pay.repository.PaySettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaySettlementServiceImpl implements PaySettlementService {

    private final PaySettlementRepository paySettlementRepository;

    @Override
    public ExternalSettlementResponse getSettlementByOrderId(String orderId) {
        PaySettlement paySettlement = paySettlementRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ApplicationException(SettlementErrorCode.PAY_SETTLEMENT_NOT_FOUND));
        return ExternalSettlementResponse.from(paySettlement);
    }
}
