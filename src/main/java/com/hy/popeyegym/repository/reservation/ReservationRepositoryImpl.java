package com.hy.popeyegym.repository.reservation;

import com.hy.popeyegym.domain.reservation.QReservation;
import com.hy.popeyegym.domain.reservation.Reservation;
import com.hy.popeyegym.domain.reservation.ReservationStatus;
import com.hy.popeyegym.domain.trainer.QTrainer;
import com.hy.popeyegym.domain.trainer.Trainer;
import com.hy.popeyegym.dto.response.ReservationResponseDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.hy.popeyegym.domain.reservation.QReservation.*;
import static com.hy.popeyegym.domain.trainer.QTrainer.*;
import static com.hy.popeyegym.dto.response.ReservationResponseDto.*;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Reservation> findScheduleOfTrainer (Long trainerId, LocalDateTime startDt, LocalDateTime endDt) {
        return jpaQueryFactory
                .select(reservation)
                .from(reservation)
                .where(
                        trainer.id.eq(trainerId),
                        reservation.status.eq(ReservationStatus.RESERVATION),
                        reservation.dateTime.between(startDt, endDt)
                )
                .fetch();
    }

    @Override
    public List<Reservation> getSchedulesOfDate(Long trainerId, LocalDateTime dateTime) {
        return jpaQueryFactory
                .selectFrom(reservation)
                .where(
                        reservation.trainer.id.eq(trainerId),
                        reservation.status.eq(ReservationStatus.RESERVATION),
                        reservation.dateTime.eq(dateTime)
                )
                .fetch();
    }

    @Override
    public List<ReservationsRes> getReservations(Long userId, String status, Long trainerId) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(reservation.user.id.eq(userId));
        builder.and(reservation.status.eq(ReservationStatus.RESERVATION));

        if(status != null) {
            LocalDateTime now = LocalDateTime.now();
            if (status.equals("s")) {
                builder.and(reservation.dateTime.gt(now));
            }
            else {
                builder.and(reservation.dateTime.lt(now));
            }
        }

        if (trainerId != null) {
            builder.and(reservation.trainer.id.eq(trainerId));
        }

        return jpaQueryFactory
                .select(Projections.fields(
                        ReservationsRes.class,
                        reservation.id.as("reservationId"),
                        reservation.dateTime,
                        trainer.name.as("trainerName")
                ))
                .from(reservation)
                .join(reservation.trainer, trainer)
                .where(builder)
                .orderBy(reservation.dateTime.desc())
                .fetch();
    }


//    @Override
//    public List<Reservation> getReservationAfterNow(Long reservationId, Long userId) {
//        return jpaQueryFactory
//                .selectFrom(reservation)
//                .where(
//                        reservation.id.eq(reservationId),
//                        reservation.user.id.eq(userId),
//                        reservation.dateTime.gt(LocalDateTime.now())
//                )
//                .fetch();
//    }
}
