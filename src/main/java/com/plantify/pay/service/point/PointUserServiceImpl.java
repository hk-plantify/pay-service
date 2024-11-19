package com.plantify.pay.service.point;

import com.plantify.pay.global.exception.errorcode.PointErrorCode;
import com.plantify.pay.util.UserInfoProvider;
import com.plantify.pay.domain.dto.response.PointUserResponse;
import com.plantify.pay.domain.entity.Point;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointUserServiceImpl implements PointUserService {

    private final PointRepository pointRepository;
    private final UserInfoProvider userInfoProvider;

    @Override
    public PointUserResponse getUserPoints() {
        Long userId = userInfoProvider.getUserInfo().userId();
        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));
        return PointUserResponse.from(point);
    }

    @Override
    public void rewardPoints(Long userId, Long rewardPoints) {
        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));

        point = point.toBuilder()
                .pointBalance(point.getPointBalance() + rewardPoints)
                .accumulatedPoints(point.getAccumulatedPoints() + rewardPoints)
                .build();
        pointRepository.save(point);
    }

}
