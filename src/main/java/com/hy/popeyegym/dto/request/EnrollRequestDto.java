package com.hy.popeyegym.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class EnrollRequestDto {

    @Data
    public static class EnrollSignUpRequestDto {

        @NotBlank(message = "유저 id 없음")
        private Long userId;
        @NotBlank(message = "트레이너 id 없음")
        private Long trainerId;
        @NotBlank(message = "횟수 없음")
        private int times;
    }

}
