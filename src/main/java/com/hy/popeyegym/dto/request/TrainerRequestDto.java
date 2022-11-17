package com.hy.popeyegym.dto.request;

import com.hy.popeyegym.domain.trainer.TrainerType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

public class TrainerRequestDto {

    @Data
    public static class EnrollReq {

        @NotBlank(message = "트레이너 이름이 없습니다")
        private String name;
        @NotBlank(message = "트레이너 타입이 없습니다")
        private TrainerType type;

        public void setType(String type) {
            this.type = TrainerType.valueOf(type);
        }
    }

    @Data
    public static class GetTrainerAllReq {
        private String nameParam;
        private TrainerType type;

        public void setType(String type) {
            this.type = TrainerType.valueOf(type);
        }
    }

}
