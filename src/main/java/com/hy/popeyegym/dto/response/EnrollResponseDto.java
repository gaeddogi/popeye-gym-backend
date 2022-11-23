package com.hy.popeyegym.dto.response;

import com.hy.popeyegym.domain.trainer.TrainerType;
import lombok.AllArgsConstructor;
import lombok.Data;

public class EnrollResponseDto {

    @Data
    @AllArgsConstructor
    public static class EnrollSignUpResponseDto {
        private Long id;
    }

    @Data
    public static class GetTrainersRes {
        private Long id;
        private String name;
        private TrainerType type;
    }

}
