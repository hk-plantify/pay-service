package com.plantify.pay.global.exception.errorcode;

import com.plantify.pay.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AccountErrorCode implements ErrorCode {

    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "계좌를 찾을 수 없습니다."),
    INVALID_ACCOUNT_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 계좌 상태입니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "계좌에 대한 접근 권한이 없습니다."),
    DUPLICATE_ACCOUNT(HttpStatus.CONFLICT, "이미 존재하는 계좌입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
