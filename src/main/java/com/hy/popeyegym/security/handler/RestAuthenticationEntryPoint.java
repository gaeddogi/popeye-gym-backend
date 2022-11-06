package com.hy.popeyegym.security.handler;

import com.hy.popeyegym.exception.ErrorResponse;
import com.hy.popeyegym.exception.exceptionType.AuthExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        authException.printStackTrace();
        log.info("Responding with unauthorized error. Message := {}", authException.getMessage());


        response.setStatus(AuthExceptionType.UNAUTHORIZED_USER.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");

        ErrorResponse errorResponse = new ErrorResponse(AuthExceptionType.UNAUTHORIZED_USER);
        response.getWriter().write(errorResponse.convertToJson());

    }
}
