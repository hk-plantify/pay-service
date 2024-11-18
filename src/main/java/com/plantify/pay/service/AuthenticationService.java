package com.plantify.pay.service;

public interface AuthenticationService {

    boolean validateAdminRole();
    void validateOwnership(Long ownerId);
    Long getUserId();
}