package com.plantify.pay.controller;

import com.plantify.pay.domain.dto.response.PointResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.PointService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay/points")
public class PointController {

    private final PointService pointService;

    @GetMapping
    public ResponseEntity<ApiResponse<PointResponse>> getPoints() {
        PointResponse response = pointService.getPoints();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<PointResponse>> getPointsByUserId(@PathVariable Long userId) {
        PointResponse response = pointService.getPointsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<PointResponse>> usePoints(@RequestParam Long pointsToUse) {
        PointResponse response = pointService.usePoints(pointsToUse);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}