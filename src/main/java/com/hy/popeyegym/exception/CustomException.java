package com.hy.popeyegym.exception;

import com.hy.popeyegym.exception.exceptionType.ExceptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final ExceptionType exceptionType;
}
