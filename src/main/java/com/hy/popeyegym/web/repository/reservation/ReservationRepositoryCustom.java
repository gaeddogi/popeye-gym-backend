package com.hy.popeyegym.web.repository.reservation;

import com.hy.popeyegym.web.domain.reservation.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.hy.popeyegym.web.dto.response.ReservationResponseDto.ReservationsRes;

public interface ReservationRepositoryCustom {

    List<Reservation> findScheduleOfTrainer(Long trainerId, LocalDateTime startDt, LocalDateTime endDt);

    List<Reservation> getSchedulesOfDate(Long trainerId, LocalDateTime dateTime);

    Page<ReservationsRes> getReservations(Long userId, String status, Long trainerId, Pageable pageable);

//    List<Reservation> getReservationAfterNow(Long reservationId, Long userId);
}
