package com.hy.popeyegym.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class TokenResponseDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class CreateTokenResponseDto {
        private String accessToken;
        private String refreshToken;
    }

}
