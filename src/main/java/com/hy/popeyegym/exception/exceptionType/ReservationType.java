package com.hy.popeyegym.exception.exceptionType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Getter
@RequiredArgsConstructor
public enum ReservationType implements ExceptionType{

    NOT_ENROLL_PT(BAD_REQUEST,"등록되지 않은 PT권 입니다."),
    NOT_FOUND(BAD_REQUEST, "존재하지 않는 예약입니다."),
    NOT_REMAINING(OK, "남은 PT권이 부족합니다."),
    EXPIRED(BAD_REQUEST, "이미 만료된 예약이라 취소할 수 없습니다."),
    ALREADY_RESERVED(BAD_REQUEST, "이미 예약된 날짜입니다."),
    ;


    private final HttpStatus httpStatus;
    private final String message;
}
