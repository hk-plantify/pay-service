package com.plantify.pay.service;

import com.plantify.pay.client.UserInfoProvider;
import com.plantify.pay.global.exception.ApplicationException;
import com.plantify.pay.global.exception.errorcode.PayErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserInfoProvider userInfoProvider;
    private final List<String> adminRoles;

    @Override
    public boolean validateAdminRole() {
        String role = userInfoProvider.getUserInfo().role();
        if (adminRoles.contains(role)) {
            return true;
        }
        throw new ApplicationException(PayErrorCode.UNAUTHORIZED_ACCESS);
    }

    @Override
    public void validateOwnership(Long ownerId) {
        Long userId = userInfoProvider.getUserInfo().userId();
        if (!userId.equals(ownerId)) {
            throw new ApplicationException(PayErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    @Override
    public Long getUserId() {
        return userInfoProvider.getUserInfo().userId();
    }
}
