package com.hy.popeyegym.web.repository.reservation;

import com.hy.popeyegym.web.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
    Optional<Reservation> findByIdAndUserId(Long reservationId, Long userId);

//    Optional<Reservation> findByTrainerIdAndDateTime(Long trainerId, LocalDateTime dateTime);
}
