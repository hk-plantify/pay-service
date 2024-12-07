package com.plantify.pay.service.point;

public interface PointService {

    void rewardPoints(Long userId, Long rewardPoints);
    void PointsToUse(Long userId, Long pointToUse);
}
