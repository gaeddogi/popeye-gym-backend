package com.hy.popeyegym.exception.exceptionType;

import org.springframework.http.HttpStatus;

public interface ExceptionType {

    String name();
    HttpStatus getHttpStatus();
    String getMessage();
}
