package com.hy.popeyegym.repository.trainer;

import com.hy.popeyegym.domain.trainer.QTrainer;
import com.hy.popeyegym.domain.trainer.TrainerType;
import com.hy.popeyegym.dto.response.TrainerResponseDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hy.popeyegym.domain.trainer.QTrainer.*;
import static com.hy.popeyegym.dto.response.TrainerResponseDto.*;

@RequiredArgsConstructor
public class TrainerRepositoryImpl implements TrainerRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetTrainerAllRes> getTrainerAllRes(String nameParam, TrainerType type) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (type != null) {
            booleanBuilder.and(trainer.type.eq(type));
        }
        if (nameParam != null) {
            booleanBuilder.and(trainer.name.contains(nameParam));
        }

        return queryFactory
                .select(Projections.fields(GetTrainerAllRes.class,
                        trainer.id,
                        trainer.name,
                        trainer.type
                ))
                .from(trainer)
                .where(booleanBuilder)
                .orderBy( trainer.type.asc(), trainer.name.asc())
                .fetch();
    }
}
