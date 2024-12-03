package com.plantify.pay.repository;

import com.plantify.pay.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUserId(Long userId);
    Optional<Account> findByAccountIdAndUserId(Long accountId, Long userId);
}
