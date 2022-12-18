package com.hy.popeyegym.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationResponseDto {

    @Data
    @AllArgsConstructor
    public static class ReservationRes {
        private Long id;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class GetScheduleOfTrainerRes {
        private Long trainerId;
        private List<ScheduleDetails> scheduleDetails;
    }

    @Data
    @Builder
    public static class ScheduleDetails {
        private Long reservationId;
        private LocalDateTime dateTime;
//        private int time;
        private boolean isMine;
    }

    @Data
    public static class ReservationsRes {
        private Long reservationId;
        private LocalDateTime dateTime;
        private String trainerName;
    }

}
