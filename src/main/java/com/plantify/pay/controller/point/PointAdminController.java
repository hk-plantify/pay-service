package com.plantify.pay.controller.point;

import com.plantify.pay.domain.dto.point.PointAdminResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.point.PointAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/points")
public class PointAdminController {

    private final PointAdminService pointAdminService;

    // 모든 사용자 포인트 조회
    @GetMapping
    public ApiResponse<List<PointAdminResponse>> getAllUserPoints() {
        List<PointAdminResponse> response = pointAdminService.getAllUserPoints();
        return ApiResponse.ok(response);
    }

    // 특정 사용자 포인트 조회
    @GetMapping("/users/{userId}")
    public ApiResponse<PointAdminResponse> getUserPointsByUserId(@PathVariable Long userId) {
        PointAdminResponse response = pointAdminService.getUserPointsByUserId(userId);
        return ApiResponse.ok(response);
    }
}