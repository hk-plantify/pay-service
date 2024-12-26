package com.plantify.pay.service.point;

public interface PointService {

    void addPoints(Long userId, Long newPoints);
    void usePoints(Long userId, Long pointToUse);
}
