package com.hy.popeyegym.web.dto.response;

import com.hy.popeyegym.web.domain.trainer.TrainerType;
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
