package com.hy.popeyegym.exception.exceptionType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum UserExceptionType implements ExceptionType {

    NOT_FOUND_USER(BAD_REQUEST,"존재하지 않는 회원 입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;


}
