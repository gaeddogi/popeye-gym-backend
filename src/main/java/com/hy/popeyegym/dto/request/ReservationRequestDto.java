package com.hy.popeyegym.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReservationRequestDto {

    /**
     * {
     *     "trainerId": ,
     *     "dates":
     *          [
     *              {"trainingDt": ,
     *              "startTime":},
     *          ]
     * }
     */
    @Data
    @Builder
    public static class ReservationReq {

        @NotBlank(message = "넘어온 트레이너 id 없음")
        private Long trainerId;
//        @NotBlank(message = "넘어온 트레이닝 받을 날짜 없음")
//        private LocalDate trainingDt;
        private List<Date> reservationDates;
//        @NotBlank(message = "넘어온 시작시간 없음")
//        private int startTime;

    }

    @Getter
    public static class Date {
        private LocalDate date;
        private int time;

        public void setDate(String dateStr) {
            int[] dateArr = Stream.of(dateStr.split("-")).mapToInt(Integer::parseInt).toArray();
            this.date = LocalDate.of(dateArr[0], dateArr[1], dateArr[2]);
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

        private LocalDate startDt;
        private LocalDate endDt;

        public LocalDate convertStringToLocalDate(String dateStr) {
            int[] dateArr = Stream.of(dateStr.split("-")).mapToInt(Integer::parseInt).toArray();

            return LocalDate.of(dateArr[0], dateArr[1], dateArr[2]);
        }

        public void setStartDt(String startDt) {
            this.startDt = convertStringToLocalDate(startDt);
        }

        public void setEndDt(String endDt) {
            this.endDt = convertStringToLocalDate(endDt);
        }
    }

}
