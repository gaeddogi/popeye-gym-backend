package com.hy.popeyegym.repository.reservation;

import com.hy.popeyegym.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
    Optional<Reservation> findByIdAndUserId(Long reservationId, Long userId);

//    Optional<Reservation> findByTrainerIdAndDateTime(Long trainerId, LocalDateTime dateTime);
}
