package com.plantify.pay.repository;

import com.plantify.pay.domain.entity.PSettlement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PSettlementRepository extends JpaRepository<PSettlement, Long> {

    Optional<PSettlement> findByPSettlementIdAndPay_Account_UserId(Long pSettlementId, Long userId);
    Page<PSettlement> findByPay_Account_UserId(Long userId, Pageable pageable);
}