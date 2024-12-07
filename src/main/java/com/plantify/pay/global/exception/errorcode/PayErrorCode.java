package com.plantify.pay.global.exception.errorcode;

import com.plantify.pay.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum PayErrorCode implements ErrorCode {

    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다."),
    PAY_NOT_FOUND(HttpStatus.NOT_FOUND, "페이를 찾을 수 없습니다."),

    PAY_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 생성된 페이가 존재합니다."),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다"),
    INVALID_PAY_INPUT(HttpStatus.BAD_REQUEST, "충전 금액은 0보다 커야 합니다."),
    INVALID_CHARGE_UNIT(HttpStatus.BAD_REQUEST, "충전 금액은 10,000원 단위로만 가능합니다."),
    INVALID_REFUND_AMOUNT(HttpStatus.BAD_REQUEST, "환불 금액이 결제 금액을 초과할 수 없습니다."),
    CONCURRENT_UPDATE(HttpStatus.CONFLICT, "동시에 처리 중인 요청이 있습니다."),
    DUPLICATE_TRANSACTION(HttpStatus.CONFLICT, "이미 진행 중인 결제가 존재합니다.");

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
