package com.hy.popeyegym.exception.exceptionType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum TrainerExceptionType implements ExceptionType {

    NOT_FOUND_TRAINER(BAD_REQUEST,"존재하지 않는 트레이너 입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;


}
