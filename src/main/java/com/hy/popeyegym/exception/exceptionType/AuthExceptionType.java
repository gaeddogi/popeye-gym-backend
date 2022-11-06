package com.hy.popeyegym.exception.exceptionType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionType implements ExceptionType{

    AUTH_EXCEPTION(UNAUTHORIZED, "인증에 실패했습니다."),
    UNAUTHORIZED_USER(UNAUTHORIZED, "로그인 필요")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
