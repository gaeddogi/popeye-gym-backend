package com.hy.popeyegym.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class AuthResponseDto {

    @Data
    @AllArgsConstructor
    @Builder
    public static class ReissueRes {
        private String token;
    }

}
