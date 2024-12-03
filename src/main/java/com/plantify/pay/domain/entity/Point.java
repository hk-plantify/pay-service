package com.plantify.pay.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long pointId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = true)
    private Long pointBalance;

    @Column(nullable = true)
    private Long accumulatedPoints;

    @Column(nullable = true)
    private Long redeemedPoints;
}
