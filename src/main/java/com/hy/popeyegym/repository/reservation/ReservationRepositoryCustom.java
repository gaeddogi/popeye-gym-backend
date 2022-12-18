package com.hy.popeyegym.repository.reservation;

import com.hy.popeyegym.domain.reservation.Reservation;
import com.hy.popeyegym.dto.response.ReservationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.hy.popeyegym.dto.response.ReservationResponseDto.*;

public interface ReservationRepositoryCustom {

    List<Reservation> findScheduleOfTrainer(Long trainerId, LocalDateTime startDt, LocalDateTime endDt);

    List<Reservation> getSchedulesOfDate(Long trainerId, LocalDateTime dateTime);

    Page<ReservationsRes> getReservations(Long userId, String status, Long trainerId, Pageable pageable);

//    List<Reservation> getReservationAfterNow(Long reservationId, Long userId);
}
