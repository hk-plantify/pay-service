package com.plantify.pay.repository;

import com.plantify.pay.domain.entity.PaySettlement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaySettlementRepository extends JpaRepository<PaySettlement, Long> {

    Page<PaySettlement> findByPayAccountUserId(Long userId, Pageable pageable);
    List<PaySettlement> findByPayAccountUserId(Long userId);
    Optional<PaySettlement> findByPaySettlementIdAndPayAccountUserId(Long paySettlementId, Long userId);
}