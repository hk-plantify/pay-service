package com.plantify.pay.service.point;

import com.plantify.pay.domain.dto.response.PointUserResponse;

public interface PointUserService {

    PointUserResponse getUserPoints();
    void rewardPoints(Long userId, Long rewardPoints);
}