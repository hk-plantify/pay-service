package com.plantify.pay.global.exception.errorcode;

import com.plantify.pay.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum PointErrorCode implements ErrorCode {

    POINT_NOT_FOUND(HttpStatus.NOT_FOUND, "포인트 정보를 찾을 수 없습니다."),
    INSUFFICIENT_POINTS(HttpStatus.BAD_REQUEST, "포인트 잔액이 부족합니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "포인트 접근 권한이 없습니다."),
    INVALID_POINT_OPERATION(HttpStatus.BAD_REQUEST, "유효하지 않은 포인트 작업 요청입니다.");

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
