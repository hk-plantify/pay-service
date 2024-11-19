package com.plantify.pay.repository;

import com.plantify.pay.domain.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUserId(Long userId);
    Page<Account> findByUserId(Long userId, Pageable pageable);
    Optional<Account> findByAccountIdAndUserId(Long accountId, Long userId);
}
