package com.hy.popeyegym.security.filter;

import com.hy.popeyegym.exception.CustomException;
import com.hy.popeyegym.exception.ErrorResponse;
import com.hy.popeyegym.exception.exceptionType.ExceptionType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws ServletException, IOException {
        try {
            chain.doFilter(req, res); // go to 'JwtAuthenticationFilter'
        } catch (CustomException e) {
            setErrorResponse(e.getExceptionType(), res);
        }
    }

    public void setErrorResponse(ExceptionType e, HttpServletResponse res) throws IOException {
        res.setStatus(e.getHttpStatus().value());
        res.setContentType("application/json; charset=UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(e);

        res.getWriter().write(errorResponse.convertToJson());
    }
}