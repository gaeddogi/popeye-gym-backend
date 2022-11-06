package com.hy.popeyegym.security.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class HeaderUtils {
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";


    /**
     * 요쳥 헤더에서 토큰 추출
     */
    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        if (StringUtils.hasText(headerValue) && headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(7, headerValue.length());
        }

        return null;
    }
}
