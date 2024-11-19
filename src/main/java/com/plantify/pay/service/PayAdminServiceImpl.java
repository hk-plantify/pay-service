package com.plantify.pay.service;

import com.plantify.pay.domain.dto.request.PayAdminRequest;
import com.plantify.pay.domain.dto.response.PayAdminResponse;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayAdminServiceImpl implements PayAdminService {

    private final PayRepository payRepository;

    @Override
    public List<PayAdminResponse> getAllPays() {
        return payRepository.findAll().stream()
                .map(PayAdminResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<PayAdminResponse> getPaysByUserId(Long userId) {
        return payRepository.findByAccountUserId(userId).stream()
                .map(PayAdminResponse::from)
                .toList();
    }

    @Override
    public PayAdminResponse updatePayByPayIdAndUserId(Long payId, Long userId, PayAdminRequest request) {
        Pay pay = payRepository.findByPayIdAndAccountUserId(payId, userId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

        pay = pay.toBuilder()
                .payStatus(request.payStatus())
                .build();

        payRepository.save(pay);
        return PayAdminResponse.from(pay);
    }
}