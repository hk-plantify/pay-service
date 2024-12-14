package com.plantify.pay.repository;

import com.plantify.pay.domain.entity.PaySettlement;
import com.plantify.pay.domain.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaySettlementRepository extends JpaRepository<PaySettlement, Long> {

    Optional<PaySettlement> findByPayUserId(Long userId);
    Page<PaySettlement> findByPayUserId(Long userId, Pageable pageable);
    Optional<PaySettlement> findByStatusAndPayUserId(Status status, Long userId);
    Optional<PaySettlement> findByOrderId(String orderId);
}