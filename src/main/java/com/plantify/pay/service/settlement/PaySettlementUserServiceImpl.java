package com.plantify.pay.service.settlement;

import com.plantify.pay.util.UserInfoProvider;
import com.plantify.pay.domain.dto.response.PaySettlementUserResponse;
import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.SettlementErrorCode;
import com.plantify.pay.repository.PaySettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaySettlementUserServiceImpl implements PaySettlementUserService {

    private final PaySettlementRepository paySettlementRepository;
    private final UserInfoProvider userInfoProvider;

    @Override
    public Page<PaySettlementUserResponse> getAllPaySettlements(Pageable pageable) {
        Long userId = userInfoProvider.getUserInfo().userId();
        return paySettlementRepository.findByPayAccountUserId(userId, pageable)
                .map(PaySettlementUserResponse::from);
    }

    @Override
    public PaySettlementUserResponse getPaySettlementById(Long paySettlementId) {
        Long userId = userInfoProvider.getUserInfo().userId();
        PaySettlement paySettlement = paySettlementRepository.findByPaySettlementIdAndPayAccountUserId(paySettlementId, userId)
                .orElseThrow(() -> new ApplicationException(SettlementErrorCode.PAY_SETTLEMENT_NOT_FOUND));
        return PaySettlementUserResponse.from(paySettlement);
    }
}
