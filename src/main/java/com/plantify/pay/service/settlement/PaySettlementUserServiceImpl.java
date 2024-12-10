package com.plantify.pay.service.settlement;

import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.TransactionType;
import com.plantify.pay.global.util.UserInfoProvider;
import com.plantify.pay.domain.dto.settlement.PaySettlementUserResponse;
import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.SettlementErrorCode;
import com.plantify.pay.repository.PaySettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaySettlementUserServiceImpl implements PaySettlementUserService {

    private final PaySettlementRepository paySettlementRepository;
    private final UserInfoProvider userInfoProvider;

    @Override
    public Page<PaySettlementUserResponse> getAllPaySettlements(Pageable pageable) {
        Long userId = userInfoProvider.getUserInfo().userId();
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return paySettlementRepository.findByPayUserId(userId, sortedPageable)
                .map(PaySettlementUserResponse::from);
    }

    @Override
    public PaySettlementUserResponse getPaySettlementByTransactionType(TransactionType transactionType) {
        Long userId = userInfoProvider.getUserInfo().userId();
        PaySettlement paySettlement = paySettlementRepository.findByTransactionTypeAndPayUserId(transactionType, userId)
                .orElseThrow(() -> new ApplicationException(SettlementErrorCode.PAY_SETTLEMENT_NOT_FOUND));
        return PaySettlementUserResponse.from(paySettlement);
    }

    @Override
    @Transactional
    public void savePaySettlement(Pay pay, TransactionType transactionType, Long amount) {
        PaySettlement paySettlement = PaySettlement.create(pay, transactionType, amount, pay.getBalance());
        paySettlementRepository.save(paySettlement);
    }
}
