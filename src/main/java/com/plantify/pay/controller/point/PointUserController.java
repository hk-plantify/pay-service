package com.plantify.pay.controller.point;

import com.plantify.pay.domain.dto.request.PointUseRequest;
import com.plantify.pay.domain.dto.response.PointUseResponse;
import com.plantify.pay.domain.dto.response.PointUserResponse;
import com.plantify.pay.global.response.ApiResponse;
import com.plantify.pay.service.point.PointUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 결제 시 포인트 사용 요청
    @PostMapping("/use")
    public ResponseEntity<ApiResponse<PointUseResponse>> usePointsForPayment(@RequestBody PointUseRequest request) {
        PointUseResponse response = pointUserService.usePoints(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

}
