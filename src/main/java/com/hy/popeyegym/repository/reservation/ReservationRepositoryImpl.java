package com.hy.popeyegym.repository.reservation;

import com.hy.popeyegym.domain.reservation.QReservation;
import com.hy.popeyegym.domain.reservation.Reservation;
import com.hy.popeyegym.domain.reservation.ReservationStatus;
import com.hy.popeyegym.domain.trainer.QTrainer;
import com.hy.popeyegym.domain.trainer.Trainer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.hy.popeyegym.domain.reservation.QReservation.*;
import static com.hy.popeyegym.domain.trainer.QTrainer.*;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Reservation> findScheduleOfTrainer (Long trainerId, LocalDate startDt, LocalDate endDt) {
        return jpaQueryFactory
                .select(reservation)
                .from(reservation)
                .where(
                        trainer.id.eq(trainerId),
                        reservation.status.eq(ReservationStatus.RESERVATION),
                        reservation.trainingDt.between(startDt, endDt)
                )
                .fetch();

//        startDt.atStartOfDay(), LocalDateTime.of(endDt, LocalTime.MAX).withNano(0)
//        LocalDateTime.of(searchParams.endDate, LocalTime.MAX).withNano(0)

    }
}
