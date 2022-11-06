package com.hy.popeyegym.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReservationRequestDto {

    /**
     * {
     *     "trainerId": ,
     *     "trainingDateTime": YYYY-MM-DD-HH
     * }
     */
    @Getter
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
