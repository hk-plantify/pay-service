package com.plantify.pay.service.point;

import com.plantify.pay.domain.dto.point.PointUseRequest;
import com.plantify.pay.domain.dto.point.PointUseResponse;
import com.plantify.pay.domain.dto.point.PointUserResponse;

public interface PointUserService {

    PointUserResponse getUserPoints();
    PointUseResponse usePoints(PointUseRequest request);
}