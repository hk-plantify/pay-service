package com.plantify.pay.service;

import com.plantify.pay.domain.dto.response.PointResponse;

public interface PointService {

    PointResponse getPoints();
    PointResponse getPointsByUserId(Long userId);
    PointResponse usePoints(Long pointsToUse);
}
