package com.plantify.pay.controller.point;

import com.plantify.pay.domain.dto.point.PointUserResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.point.PointUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pay/points")
public class PointUserController {

    private final PointUserService pointUserService;

    // 자신의 포인트 조회
    @GetMapping
    public ApiResponse<PointUserResponse> getUserPoints() {
        PointUserResponse response = pointUserService.getUserPoints();
        return ApiResponse.ok(response);
    }
}
