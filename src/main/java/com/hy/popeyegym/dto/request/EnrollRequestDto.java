package com.hy.popeyegym.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class EnrollRequestDto {

    @Data
    public static class EnrollSignUpRequestDto {

        @NotBlank(message = "넘어온 유저 id 없음")
        private Long userId;
        @NotBlank(message = "넘어온 트레이너 id 없음")
        private Long trainerId;
        private int times;
    }

}
