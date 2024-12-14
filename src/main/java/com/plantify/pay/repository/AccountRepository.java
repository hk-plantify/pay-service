package com.plantify.pay.repository;

import com.plantify.pay.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByAccountId(Long accountId);
    long countByPayUserId(Long userId);
    boolean existsByAccountNum(Long accountNum);
    List<Account> findByPayUserId(Long userId);
    Optional<Account> findFirstByPayUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Account> findByAccountIdAndPayUserId(Long accountId, Long userId);
}
