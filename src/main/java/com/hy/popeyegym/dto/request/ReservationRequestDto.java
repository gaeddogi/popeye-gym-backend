package com.hy.popeyegym.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class ReservationRequestDto {

    /**
     * {
     *     "trainerId": ,
     *     "trainingDateTime": YYYY-MM-DD-HH
     * }
     */
    @Getter
    @ToString
    public static class ReservationReq {

        @NotBlank(message = "넘어온 트레이너 id 없음")
        private Long trainerId;
        private LocalDateTime dateTime;

        public void setDateTime(String dateTimeStr) {
            int[] dateArr = Stream.of(dateTimeStr.split("-")).mapToInt(Integer::parseInt).toArray();
            this.dateTime = LocalDateTime.of(dateArr[0], dateArr[1], dateArr[2], dateArr[3], 0);
        }

    }

    @Data
    public static class ReserCancelRequestDto {

        @NotBlank(message = "넘어온 유저 id 없음")
        private Long userId;

        private Long reservationId;

    }

    @Data
    public static class getScheduleOfTrainerReq {

        private LocalDateTime startDt;
        private LocalDateTime endDt;

        public LocalDateTime convertStringToLocalDate(String dateStr) {
            int[] dateArr = Stream.of(dateStr.split("-")).mapToInt(Integer::parseInt).toArray();

            return LocalDateTime.of(dateArr[0], dateArr[1], dateArr[2], 0, 0);
        }

        public void setStartDt(String startDt) {
            this.startDt = convertStringToLocalDate(startDt);
        }

        public void setEndDt(String endDt) {
            this.endDt = convertStringToLocalDate(endDt);
        }
    }

    @Data
    public static class ReservationsReq {
        private String status;
        private Long trainerId;
    }

}
