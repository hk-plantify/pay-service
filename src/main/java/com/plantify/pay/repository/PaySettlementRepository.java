package com.plantify.pay.repository;

import com.plantify.pay.domain.entity.PaySettlement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaySettlementRepository extends JpaRepository<PaySettlement, Long> {

    Page<PaySettlement> findByPayUserId(Long userId, Pageable pageable);
    List<PaySettlement> findByPayUserId(Long userId);
    Optional<PaySettlement> findByPaySettlementIdAndPayUserId(Long paySettlementId, Long userId);
}