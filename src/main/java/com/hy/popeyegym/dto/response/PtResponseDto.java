package com.hy.popeyegym.dto.response;

import com.hy.popeyegym.domain.trainer.TrainerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class PtResponseDto {

    @Data
    public static class GetUserPtInfoRes {
        private Long trainerId;
        private String name;
        private int quantity;
        private TrainerType type;
    }

}
