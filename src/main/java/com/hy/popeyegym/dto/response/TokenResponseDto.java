package com.hy.popeyegym.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
