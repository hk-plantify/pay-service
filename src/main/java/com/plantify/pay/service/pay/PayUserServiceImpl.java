package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.TransactionStatusMessage;
import com.plantify.pay.domain.dto.request.PayUserRequest;
import com.plantify.pay.domain.dto.response.PayUserResponse;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.PayStatus;
import com.plantify.pay.domain.entity.Point;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.global.exception.errorcode.PointErrorCode;
import com.plantify.pay.kafka.KafkaProducer;
import com.plantify.pay.repository.PayRepository;
import com.plantify.pay.repository.PointRepository;
import com.plantify.pay.util.UserInfoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayUserServiceImpl implements PayUserService, PayInternalService {

    private final PayRepository payRepository;
    private final UserInfoProvider userInfoProvider;
    private final KafkaProducer kafkaProducer;
    private final PointRepository pointRepository;

    @Override
    public Page<PayUserResponse> getAllPays(Pageable pageable) {
        Long userId = userInfoProvider.getUserInfo().userId();
        return payRepository.findByAccountUserId(userId, pageable)
                .map(PayUserResponse::from);
    }

    @Override
    public PayUserResponse createPay(PayUserRequest request) {
        Long userId = userInfoProvider.getUserInfo().userId();
        if (payRepository.existsByAccountUserId(userId)) {
            throw new ApplicationException(PayErrorCode.PAY_ALREADY_EXISTS);
        }

        Pay pay = request.toEntity(userId);
        payRepository.save(pay);
        return PayUserResponse.from(pay);
    }

    @Override
    public PayUserResponse balanceRechargePay(Long payId, PayUserRequest request) {
        Long userId = userInfoProvider.getUserInfo().userId();
        Pay pay = payRepository.findByPayIdAndAccountUserId(payId, userId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

        pay = pay.toBuilder()
                .balance(pay.getBalance() + request.balance())
                .build();

        payRepository.save(pay);
        return PayUserResponse.from(pay);
    }

    @Override
    public void handleTransactionSuccess(Long transactionId, Long userId, Long amount) {
        Pay pay = payRepository.findById(transactionId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));
        pay = pay.toBuilder()
                .payStatus(PayStatus.SUCCESS)
                .build();
        payRepository.save(pay);

        Long rewardPoints = Math.round(amount * 0.005);
        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));
        point = point.toBuilder()
                .pointBalance(point.getPointBalance() + rewardPoints)
                .accumulatedPoints(point.getAccumulatedPoints() + rewardPoints)
                .build();
        pointRepository.save(point);

        kafkaProducer.sendMessage("transaction-success", new TransactionStatusMessage(transactionId, userId, amount, "SUCCESS"));
    }

    @Override
    public void handleTransactionFailure(Long transactionId, Long userId) {
        Pay pay = payRepository.findById(transactionId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));
        pay = pay.toBuilder()
                .payStatus(PayStatus.FAILED)
                .build();
        payRepository.save(pay);

        kafkaProducer.sendMessage("transaction-failure", new TransactionStatusMessage(transactionId, userId, null, "FAILED"));
    }
}
