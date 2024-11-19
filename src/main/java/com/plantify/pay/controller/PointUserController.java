package com.plantify.pay.controller;

import com.plantify.pay.domain.dto.response.PointUserResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.PointUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/points")
public class PointUserController {

    private final PointUserService pointUserService;

    // 자신의 포인트 조회
    @GetMapping
    public ResponseEntity<ApiResponse<PointUserResponse>> getUserPoints() {
        PointUserResponse response = pointUserService.getUserPoints();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

}
