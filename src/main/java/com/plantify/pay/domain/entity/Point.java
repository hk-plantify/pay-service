package com.plantify.pay.domain.entity;

import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PointErrorCode;
import com.plantify.pay.global.util.BaseEntity;
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

    @Column(nullable = false)
    private Long pointBalance;

    @Column(nullable = false)
    private Long accumulatedPoints;

    @Column(nullable = false)
    private Long redeemedPoints;

    public Point init(Long userId) {
        this.userId = userId;
        this.pointBalance = 0L;
        this.accumulatedPoints = 0L;
        this.redeemedPoints = 0L;
        return this;
    }

    public Point validatePoint(long use){
        if (this.pointBalance < use) {
            throw new ApplicationException(PointErrorCode.INSUFFICIENT_POINTS);
        }
        return this;
    }

    public void updatePoint(long newPoint){
        this.pointBalance += newPoint;
        this.accumulatedPoints += newPoint;
    }

    public void usePoint(long use){
        this.pointBalance -= use;
        this.redeemedPoints += use;
    }
}
