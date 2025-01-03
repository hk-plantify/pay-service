package com.plantify.pay.service.settlement;

import com.plantify.pay.domain.dto.settlement.PaySettlementRequest;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.Status;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.global.util.UserInfoProvider;
import com.plantify.pay.domain.dto.settlement.PaySettlementUserResponse;
import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.SettlementErrorCode;
import com.plantify.pay.repository.PayRepository;
import com.plantify.pay.repository.PaySettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
    public Page<PaySettlementUserResponse> getPaySettlementByStatus(Status status, Pageable pageable) {
        Long userId = userInfoProvider.getUserInfo().userId();
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return paySettlementRepository.findByStatusAndPayUserId(status, userId, sortedPageable)
                .map(PaySettlementUserResponse::from);
    }

    @Override
    public Long getPaySettlementAmount() {
        Long userId = userInfoProvider.getUserInfo().userId();
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        Long totalAmountByUserIdAndMonth = paySettlementRepository.getTotalAmountByUserIdAndMonth(userId, currentMonth, currentYear);
        return totalAmountByUserIdAndMonth != null ? totalAmountByUserIdAndMonth : 0;
    }
}
