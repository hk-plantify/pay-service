package com.plantify.pay.controller;

import com.plantify.pay.domain.dto.response.PointAdminResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.PointAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<List<PointAdminResponse>>> getAllUserPoints() {
        List<PointAdminResponse> response = pointAdminService.getAllUserPoints();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 특정 사용자 포인트 조회
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<PointAdminResponse>> getUserPointsByUserId(@PathVariable Long userId) {
        PointAdminResponse response = pointAdminService.getUserPointsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}