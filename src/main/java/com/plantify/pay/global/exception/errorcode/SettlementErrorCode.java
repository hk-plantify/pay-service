package com.plantify.pay.global.exception.errorcode;

import com.plantify.pay.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum SettlementErrorCode implements ErrorCode {

    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다."),
    PAY_SETTLEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제 정산 내역을 찾을 수 없습니다."),
    INVALID_TRANSACTION_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 거래 유형입니다."),
    TRANSACTION_AMOUNT_MISMATCH(HttpStatus.BAD_REQUEST, "거래 금액이 일치하지 않습니다."),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다.");

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