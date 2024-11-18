package com.plantify.pay.repository;

import com.plantify.pay.domain.entity.Pay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayRepository extends JpaRepository<Pay, Long> {

    Optional<Pay> findById(Long payId);
    Page<Pay> findByAccount_UserId(Long userId, Pageable pageable);
}
