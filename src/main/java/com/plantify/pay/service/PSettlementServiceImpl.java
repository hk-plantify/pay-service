package com.plantify.pay.service;

import com.plantify.pay.domain.dto.response.PSettlementResponse;
import com.plantify.pay.domain.entity.PSettlement;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.repository.PSettlementRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PSettlementServiceImpl implements PSettlementService {

    private final PSettlementRepository settlementRepository;
    private final AuthenticationService authenticationService;

    @Override
    public Page<PSettlementResponse> getUserSettlements(Pageable pageable) {
        Long userId = authenticationService.getUserId();
        return settlementRepository.findByPay_Account_UserId(userId, pageable)
                .map(PSettlementResponse::from);
    }

    @Override
    public PSettlementResponse getSettlementById(Long settlementId) {
        Long userId = authenticationService.getUserId();

        PSettlement settlement = settlementRepository.findByPSettlementIdAndPay_Account_UserId(settlementId, userId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAYMENT_NOT_FOUND));

        return PSettlementResponse.from(settlement);
    }

    @Override
    public Page<PSettlementResponse> getSettlementsByUserId(Long userId, Pageable pageable) {
        authenticationService.validateAdminRole();
        return settlementRepository.findByPay_Account_UserId(userId, pageable)
                .map(PSettlementResponse::from);
    }
}