package com.hy.popeyegym.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

public class UserResponseDto {

    @Data
    @AllArgsConstructor
    public static class UserSignUpResponseDto {
        private Long id;
    }

    @Data
    public static class UserLoginResponseDto {
        private Long id;
    }


}
