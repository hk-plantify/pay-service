package com.plantify.pay.service.settlement;

import com.plantify.pay.global.util.UserInfoProvider;
import com.plantify.pay.domain.dto.settlement.PaySettlementUserResponse;
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
public class PaySettlementUserServiceImpl implements PaySettlementUserService {

    private final PaySettlementRepository paySettlementRepository;
    private final UserInfoProvider userInfoProvider;

    @Override
    public List<PaySettlementUserResponse> getAllPaySettlements() {
        Long userId = userInfoProvider.getUserInfo().userId();
        return paySettlementRepository.findByPayUserId(userId)
                .stream()
                .map(PaySettlementUserResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public PaySettlementUserResponse getPaySettlementById(Long paySettlementId) {
        Long userId = userInfoProvider.getUserInfo().userId();
        PaySettlement paySettlement = paySettlementRepository.findByPaySettlementIdAndPayUserId(paySettlementId, userId)
                .orElseThrow(() -> new ApplicationException(SettlementErrorCode.PAY_SETTLEMENT_NOT_FOUND));
        return PaySettlementUserResponse.from(paySettlement);
    }
}
