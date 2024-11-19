package com.plantify.pay.service;

import com.plantify.pay.domain.dto.response.PaySettlementAdminResponse;
import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.SettlementErrorCode;
import com.plantify.pay.repository.PaySettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaySettlementAdminServiceImpl implements PaySettlementAdminService {

    private final PaySettlementRepository paySettlementRepository;

    @Override
    public List<PaySettlementAdminResponse> getAllPaySettlements() {
        return paySettlementRepository.findAll().stream()
                .map(PaySettlementAdminResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public PaySettlementAdminResponse getPaySettlementById(Long paySettlementId) {
        PaySettlement paySettlement = paySettlementRepository.findById(paySettlementId)
                .orElseThrow(() -> new ApplicationException(SettlementErrorCode.PAY_SETTLEMENT_NOT_FOUND));
        return PaySettlementAdminResponse.from(paySettlement);
    }

    @Override
    public List<PaySettlementAdminResponse> getPaySettlementsByUserId(Long userId) {
        return paySettlementRepository.findByPayAccountUserId(userId).stream()
                .map(PaySettlementAdminResponse::from)
                .collect(Collectors.toList());
    }
}
