package com.hy.popeyegym.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class PtResponseDto {

    @Data
    @Builder
    public static class GetUserPtInfo {
        private Long trainerId;
        private String name;
        private int quantity;
    }

}
