package com.hy.popeyegym.dto.response;

import com.hy.popeyegym.domain.trainer.TrainerType;
import lombok.AllArgsConstructor;
import lombok.Data;

public class TrainerResponseDto {

    @Data
    @AllArgsConstructor
    public static class EnrollRes {
        private Long id;
    }

    @Data
    public static class GetTrainerAllRes {
        private Long id;
        private String name;
        private TrainerType type;
    }


}
