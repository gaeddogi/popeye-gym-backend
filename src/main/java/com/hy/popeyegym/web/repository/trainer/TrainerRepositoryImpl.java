package com.hy.popeyegym.web.repository.trainer;

import com.hy.popeyegym.web.domain.trainer.TrainerType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.hy.popeyegym.web.domain.trainer.QTrainer.trainer;
import static com.hy.popeyegym.web.dto.response.TrainerResponseDto.GetTrainerAllRes;

@RequiredArgsConstructor
public class TrainerRepositoryImpl implements TrainerRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GetTrainerAllRes> getTrainerAllRes(String nameParam, TrainerType type, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (type != null) {
            booleanBuilder.and(trainer.type.eq(type));
        }
        if (nameParam != null) {
            booleanBuilder.and(trainer.name.contains(nameParam));
        }

        List<GetTrainerAllRes> contents = queryFactory
                .select(Projections.fields(GetTrainerAllRes.class,
                        trainer.id,
                        trainer.name,
                        trainer.type
                ))
                .from(trainer)
                .where(booleanBuilder)
                .orderBy(trainer.type.asc(), trainer.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long total = queryFactory
                .select(trainer.count())
                .from(trainer)
                .where(booleanBuilder)
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }
}
