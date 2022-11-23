package com.hy.popeyegym.repository.pt;

import com.hy.popeyegym.domain.pt.QPt;
import com.hy.popeyegym.domain.reservation.QReservation;
import com.hy.popeyegym.domain.reservation.ReservationStatus;
import com.hy.popeyegym.domain.trainer.QTrainer;
import com.hy.popeyegym.domain.trainer.Trainer;
import com.hy.popeyegym.domain.user.QUser;
import com.hy.popeyegym.dto.response.PtResponseDto;
import com.hy.popeyegym.exception.exceptionType.ReservationType;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.hy.popeyegym.domain.pt.QPt.*;
import static com.hy.popeyegym.domain.reservation.QReservation.*;
import static com.hy.popeyegym.domain.trainer.QTrainer.*;
import static com.hy.popeyegym.domain.user.QUser.*;
import static com.hy.popeyegym.dto.response.PtResponseDto.*;

@RequiredArgsConstructor
public class PtRepositoryImpl implements PtRepositoryCustom {

    private final JPAQueryFactory queryFactory;
//    select *
//    from pt p
//    join trainer t on t.trainer_id = p.trainer_id
//    where p.user_id = 1 and
//    (p.quantity > 0 or
//    (select count(*) from reservation r where r.trainer_id = t.trainer_id and r.status = 'RESERVATION' and r.date_time > now() and r.user_id = 1) > 0);

    @Override
    public List<GetUserPtInfoRes> getPtTrainers(Long userId) {
//        ist {trainerId, name, quantity, type}

            return queryFactory
                .select(Projections.fields(
                        GetUserPtInfoRes.class,
                        pt.quantity,
                        trainer.id.as("trainerId"),
                        trainer.name,
                        trainer.type.as("type")
                ))
                .from(pt)
                .join(pt.trainer, trainer)
                .where(
                        pt.user.id.eq(userId)
                        .and(
                                pt.quantity.gt(0)
                                .or(JPAExpressions
                                        .select(reservation.count())
                                        .from(reservation)
                                        .where(reservation.trainer.id.eq(pt.trainer.id),
                                                reservation.status.eq(ReservationStatus.RESERVATION),
                                                reservation.dateTime.gt(LocalDateTime.now()),
                                                user.id.eq(userId)).gt(0L)
                                )
                        )
                )
                .fetch();
    }
}
