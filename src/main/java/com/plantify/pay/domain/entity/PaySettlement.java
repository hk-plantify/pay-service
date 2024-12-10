package com.plantify.pay.domain.entity;

import com.plantify.pay.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaySettlement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long paySettlementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payId", nullable = false)
    private Pay pay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Long balanceAfter;

    public static PaySettlement create(Pay pay, TransactionType transactionType, Long amount, Long balanceAfter) {
        return new PaySettlement(null, pay, transactionType, amount, balanceAfter);
    }
}
