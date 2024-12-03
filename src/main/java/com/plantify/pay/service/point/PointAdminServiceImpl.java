package com.plantify.pay.service.point;

import com.plantify.pay.domain.dto.point.PointAdminResponse;
import com.plantify.pay.domain.entity.Point;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PointErrorCode;
import com.plantify.pay.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointAdminServiceImpl implements PointAdminService {

    private final PointRepository pointRepository;

    @Override
    public List<PointAdminResponse> getAllUserPoints() {
        return pointRepository.findAll().stream()
                .map(PointAdminResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public PointAdminResponse getUserPointsByUserId(Long userId) {
        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));
        return PointAdminResponse.from(point);
    }
}