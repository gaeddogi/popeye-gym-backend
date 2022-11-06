package com.hy.popeyegym.exception.exceptionType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Getter
@RequiredArgsConstructor
public enum ReservationErrorType implements ExceptionType{

    NOT_ENROLL_PT(BAD_REQUEST,"등록되지 않은 PT권 입니다."),
    NOT_CANCEL(BAD_REQUEST, "해당 회원의 예약이 아닙니다."),
    NOT_REMAINING(OK, "남은 PT권이 부족합니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
