package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.pay.PayAdminResponse;
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
        return payRepository.findByUserId(userId).stream()
                .map(PayAdminResponse::from)
                .toList();
    }
}