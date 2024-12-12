package com.plantify.pay.service.pay;

import com.plantify.pay.client.TransactionServiceClient;
import com.plantify.pay.domain.dto.kafka.*;
import com.plantify.pay.domain.dto.pay.ExternalSettlementResponse;
import com.plantify.pay.domain.dto.pay.PayBalanceResponse;
import com.plantify.pay.domain.entity.*;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.AuthErrorCode;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.global.exception.errorcode.PointErrorCode;
import com.plantify.pay.global.util.UserInfoProvider;
import com.plantify.pay.jwt.JwtProvider;
import com.plantify.pay.repository.AccountRepository;
import com.plantify.pay.repository.PayRepository;
import com.plantify.pay.repository.PointRepository;
import com.plantify.pay.service.point.PointService;
import com.plantify.pay.service.settlement.PaySettlementUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayServiceImpl implements PayService {

    private final JwtProvider jwtProvider;
    private final TransactionServiceClient transactionServiceClient;
    private final PayInternalService payInternalService;
    private final PayRepository payRepository;
    private final PointRepository pointRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public PaymentResponse initiatePayment(TransactionRequest request) {
        return payInternalService.payTransaction(request, true);
    }

    @Override
    @Transactional
    public TransactionStatusResponse getTransactionStatus(String token) {
//        String token = jwtProvider.extractTokenFromHeader(authorizationHeader);
        if (token == null || !jwtProvider.validateToken(token)) {
            throw new ApplicationException(AuthErrorCode.INVALID_TOKEN);
        }

        Long transactionId = jwtProvider.getClaims(token).get("id", Long.class);
        TransactionResponse response = transactionServiceClient.getTransactionById(transactionId).getData();

        Point point = pointRepository.findByUserId(response.userId())
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));
        Pay pay = payRepository.findByUserId(response.userId())
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));
        Account account = accountRepository.findByPayUserId(response.userId())
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

        return new TransactionStatusResponse(
                response.transactionId(),
                response.userId(),
                response.sellerId(),
                response.orderId(),
                response.orderName(),
                response.transactionType(),
                response.status(),
                response.amount(),
                response.redirectUri(),
                response.createdAt(),
                response.updatedAt(),
                point.getPointBalance(),
                pay.getBalance(),
                account.getAccountNum(),
                account.getBankName()
        );
    }

    @Override
    @Transactional
    public ProcessPaymentResponse verifyAndProcessPayment(String token, Long pointToUse) {
//        String token = jwtProvider.extractTokenFromHeader(authorizationHeader);
        return payInternalService.processPayment(token, pointToUse);
    }

    @Override
    @Transactional
    public RefundResponse refund(TransactionRequest request) {
        return payInternalService.refundTransaction(request);
    }

    @Override
    public PayBalanceResponse checkPayBalance(PayBalanceRequest request) {
        Pay pay = payRepository.findByUserId(request.userId())
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

        if (request.amount() > pay.getBalance()) {
            throw new ApplicationException(PayErrorCode.INSUFFICIENT_BALANCE);
        }

        return new PayBalanceResponse(pay.getBalance());
    }
}
