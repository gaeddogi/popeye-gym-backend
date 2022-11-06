package com.hy.popeyegym.repository.pt;

import com.hy.popeyegym.domain.pt.QPt;
import com.hy.popeyegym.domain.trainer.QTrainer;
import com.hy.popeyegym.domain.trainer.Trainer;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hy.popeyegym.domain.pt.QPt.*;
import static com.hy.popeyegym.domain.trainer.QTrainer.*;

@RequiredArgsConstructor
public class PtRepositoryImpl implements PtRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Tuple> getPtTrainers(Long userId) {
        return queryFactory
                .select(trainer, pt)
                .from(pt)
                .join(pt.trainer, trainer)
                .where(pt.user.id.eq(userId), pt.quantity.gt(0))
                .fetch();
    }
}
