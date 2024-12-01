package com.plantify.pay.service.pay;

import com.plantify.pay.client.TransactionServiceClient;
import com.plantify.pay.domain.dto.kafka.ExternalTransactionResponse;
import com.plantify.pay.domain.dto.kafka.TransactionRequest;
import com.plantify.pay.domain.dto.kafka.TransactionResponse;
import com.plantify.pay.domain.dto.kafka.TransactionStatusResponse;
import com.plantify.pay.domain.dto.pay.PayUserRequest;
import com.plantify.pay.domain.dto.pay.PayUserResponse;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.AuthErrorCode;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.jwt.JwtProvider;
import com.plantify.pay.repository.PayRepository;
import com.plantify.pay.util.UserInfoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayUserServiceImpl implements PayUserService, PayTransactionService {

    private final PayRepository payRepository;
    private final UserInfoProvider userInfoProvider;
    private final PayInternalService payInternalService;
    private final JwtProvider jwtProvider;
    private final TransactionServiceClient transactionServiceClient;


    @Override
    @Transactional(readOnly = true)
    public PayUserResponse getPay() {
        Long userId = userInfoProvider.getUserInfo().userId();
        Pay pay = payRepository.findByAccountUserId(userId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));
        return PayUserResponse.from(pay);
    }

    @Override
    @Transactional
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
    @Transactional
    public PayUserResponse balanceRechargePay(PayUserRequest request) {
        Long userId = userInfoProvider.getUserInfo().userId();
        Pay updatedPay = payInternalService.rechargeBalance(userId, request.balance());
        return PayUserResponse.from(updatedPay);
    }

    @Override
    @Transactional
    public TransactionResponse createTransaction(Long sellerId, TransactionRequest request) {
        return payInternalService.executeTransaction(sellerId, request, false);
    }

    @Override
    @Transactional
    public ExternalTransactionResponse createExternalTransaction(Long sellerId, TransactionRequest request) {
        TransactionResponse transactionResponse = payInternalService
                .executeTransaction(sellerId, request, true);

        String token = jwtProvider.createAccessToken(transactionResponse.transactionId());
        return ExternalTransactionResponse.from(transactionResponse, token);
    }

    @Override
    @Transactional
    public TransactionResponse refundTransaction(Long sellerId, TransactionRequest request) {
        return payInternalService.refundTransaction(sellerId, request);
    }

    @Override
    public TransactionStatusResponse getTransactionStatus(String token) {
        if (token == null || !jwtProvider.validateToken(token)) {
            throw new ApplicationException(AuthErrorCode.INVALID_TOKEN);
        }
        Long transactionId = jwtProvider.getClaims(token).get("id", Long.class);
        TransactionResponse transactionResponse = transactionServiceClient.getTransactionById(transactionId);
        return TransactionStatusResponse.from(transactionResponse);
    }
}
