package com.hy.popeyegym.repository.enroll;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hy.popeyegym.domain.enroll.QEnroll.enroll;
import static com.hy.popeyegym.domain.trainer.QTrainer.trainer;
import static com.hy.popeyegym.dto.response.EnrollResponseDto.GetTrainersRes;

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
