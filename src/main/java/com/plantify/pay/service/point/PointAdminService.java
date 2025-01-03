package com.plantify.pay.service.point;

import com.plantify.pay.domain.dto.point.PointAdminResponse;

import java.util.List;

public interface PointAdminService {

    List<PointAdminResponse> getAllUserPoints();
    PointAdminResponse getUserPointsByUserId(Long userId);
}