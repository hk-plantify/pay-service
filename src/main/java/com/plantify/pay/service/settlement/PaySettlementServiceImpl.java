package com.plantify.pay.service.settlement;

import com.plantify.pay.domain.dto.pay.ExternalSettlementResponse;
import com.plantify.pay.domain.dto.settlement.PaySettlementRequest;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.domain.entity.Status;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.global.exception.errorcode.SettlementErrorCode;
import com.plantify.pay.repository.PayRepository;
import com.plantify.pay.repository.PaySettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaySettlementServiceImpl implements PaySettlementService {

    private final PaySettlementRepository paySettlementRepository;
    private final PayRepository payRepository;

    @Override
    @Transactional
    public void savePaySettlement(PaySettlementRequest request) {
        Long userId = request.userId();
        Pay pay = payRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

        PaySettlement savedPaySettlement = request.toEntity(pay);
        paySettlementRepository.save(savedPaySettlement);
    }

    @Override
    public ExternalSettlementResponse getSettlementByOrderId(String orderId) {
        PaySettlement paySettlement = paySettlementRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ApplicationException(SettlementErrorCode.PAY_SETTLEMENT_NOT_FOUND));
        return ExternalSettlementResponse.from(paySettlement);
    }

    @Override
    public PaySettlement updateSettlementStatus(String orderId, Status status) {
        PaySettlement paySettlement = paySettlementRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ApplicationException(SettlementErrorCode.PAY_SETTLEMENT_NOT_FOUND))
                .updateStatus(status);
        return paySettlementRepository.save(paySettlement);
    }
}
