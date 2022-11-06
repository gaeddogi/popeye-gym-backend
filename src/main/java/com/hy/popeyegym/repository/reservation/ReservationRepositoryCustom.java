package com.hy.popeyegym.repository.reservation;

import com.hy.popeyegym.domain.reservation.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepositoryCustom {

    List<Reservation> findScheduleOfTrainer(Long trainerId, LocalDate startDt, LocalDate endDt);
}
