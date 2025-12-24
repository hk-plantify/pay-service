package com.plantify.pay.service.pay.query;

import com.plantify.pay.client.TransactionServiceClient;
import com.plantify.pay.domain.dto.process.TransactionResponse;
import com.plantify.pay.domain.dto.process.TransactionStatusResponse;
import com.plantify.pay.domain.entity.Account;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.Point;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.AccountErrorCode;
import com.plantify.pay.global.exception.errorcode.AuthErrorCode;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.global.exception.errorcode.PointErrorCode;
import com.plantify.pay.jwt.JwtProvider;
import com.plantify.pay.repository.AccountRepository;
import com.plantify.pay.repository.PayRepository;
import com.plantify.pay.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentStatusQuery {

    private final JwtProvider jwtProvider;
    private final TransactionServiceClient transactionClient;
    private final PayRepository payRepository;
    private final PointRepository pointRepository;
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public TransactionStatusResponse getStatus(String token) {

        if (token == null || !jwtProvider.validateToken(token)) {
            throw new ApplicationException(AuthErrorCode.INVALID_TOKEN);
        }

        Long transactionId = jwtProvider.getClaims(token).get("id", Long.class);

        TransactionResponse tx =
                transactionClient.getTransactionById(transactionId).getData();

        Pay pay = payRepository.findByUserId(tx.userId())
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));

        Point point = pointRepository.findByUserId(tx.userId())
                .orElseThrow(() -> new ApplicationException(PointErrorCode.POINT_NOT_FOUND));

        Account account = accountRepository
                .findFirstByPayUserIdOrderByCreatedAtDesc(tx.userId())
                .orElseThrow(() -> new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND));

        return TransactionStatusResponse.from(tx, pay, point, account);
    }
}
