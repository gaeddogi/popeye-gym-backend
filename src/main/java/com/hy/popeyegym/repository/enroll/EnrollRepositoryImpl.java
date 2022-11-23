package com.hy.popeyegym.repository.enroll;

import com.hy.popeyegym.domain.enroll.QEnroll;
import com.hy.popeyegym.domain.trainer.QTrainer;
import com.hy.popeyegym.dto.response.EnrollResponseDto;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hy.popeyegym.domain.enroll.QEnroll.*;
import static com.hy.popeyegym.domain.trainer.QTrainer.*;
import static com.hy.popeyegym.dto.response.EnrollResponseDto.*;

@RequiredArgsConstructor
public class EnrollRepositoryImpl implements EnrollRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetTrainersRes> getTrainers(Long userId) {
        return queryFactory
                .select(Projections.fields(
                        GetTrainersRes.class,
                        trainer.id,
                        trainer.name,
                        trainer.type)).distinct()
                .from(enroll)
                .join(enroll.trainer, trainer)
                .where(enroll.user.id.eq(userId))
                .fetch();
    }
}
