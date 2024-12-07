package com.plantify.pay.service.pay;

import com.plantify.pay.domain.dto.account.AccountUserRequest;
import com.plantify.pay.domain.dto.pay.PayUserRequest;
import com.plantify.pay.domain.dto.pay.PayUserResponse;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.Point;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.AccountErrorCode;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import com.plantify.pay.repository.AccountRepository;
import com.plantify.pay.repository.PayRepository;
import com.plantify.pay.global.util.UserInfoProvider;
import com.plantify.pay.repository.PointRepository;
import com.plantify.pay.service.account.AccountUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayUserServiceImpl implements PayUserService {

    private final PayRepository payRepository;
    private final UserInfoProvider userInfoProvider;
    private final PayInternalService payInternalService;
    private final AccountUserService accountUserService;
    private final AccountRepository accountRepository;
    private final PointRepository pointRepository;

    @Override
    @Transactional(readOnly = true)
    public PayUserResponse getPay() {
        Long userId = userInfoProvider.getUserInfo().userId();
        Pay pay = payRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(PayErrorCode.PAY_NOT_FOUND));
        return PayUserResponse.from(pay);
    }

    @Override
    @Transactional
    public PayUserResponse createPay(AccountUserRequest request) {
        Long userId = userInfoProvider.getUserInfo().userId();
        if (payRepository.existsByUserId(userId)) {
            throw new ApplicationException(PayErrorCode.PAY_ALREADY_EXISTS);
        }

        Pay pay = new Pay();
        pay.init(userId);
        payRepository.save(pay);

        Point point = new Point();
        point.init(userId);
        pointRepository.save(point);

        accountUserService.createAccount(request);

        return PayUserResponse.from(pay);
    }

    @Override
    @Transactional
    public PayUserResponse balanceRechargePay(PayUserRequest request) {
        Long userId = userInfoProvider.getUserInfo().userId();
        if (!accountRepository.existsByAccountId(request.accountId())) {
            throw new ApplicationException(AccountErrorCode.ACCOUNT_NOT_FOUND);
        }

        Pay updatedPay = payInternalService.rechargeBalance(userId, request.balance());
        return PayUserResponse.from(updatedPay);
    }
}
