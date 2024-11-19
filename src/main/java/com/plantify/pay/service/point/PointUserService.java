package com.plantify.pay.service.point;

import com.plantify.pay.domain.dto.request.PointUseRequest;
import com.plantify.pay.domain.dto.response.PointUseResponse;
import com.plantify.pay.domain.dto.response.PointUserResponse;

public interface PointUserService {

    PointUserResponse getUserPoints();
    PointUseResponse usePoints(PointUseRequest request);
    void rewardPoints(Long userId, Long rewardPoints);
}