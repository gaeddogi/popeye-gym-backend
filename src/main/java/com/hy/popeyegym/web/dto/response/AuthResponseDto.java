package com.hy.popeyegym.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class AuthResponseDto {

    @Data
    @AllArgsConstructor
    @Builder
    public static class ReissueRes {
        @Schema(description = "엑세스 토큰")
        private String token;
    }

}
