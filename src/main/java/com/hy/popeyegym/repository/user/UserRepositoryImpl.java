package com.hy.popeyegym.repository.user;

import com.hy.popeyegym.domain.user.QUser;
import com.hy.popeyegym.dto.response.UserResponseDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hy.popeyegym.domain.user.QUser.*;
import static com.hy.popeyegym.dto.response.UserResponseDto.*;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetUserAllRes> getUserAll(String emailParam) {
        BooleanBuilder builder = new BooleanBuilder();

        if (emailParam != null) {
            builder.and(user.email.contains(emailParam));
        }


        return queryFactory
                .select(Projections.fields(GetUserAllRes.class,
                        user.id,
                        user.email
                ))
                .from(user)
                .where(builder)
                .orderBy(user.email.asc())
                .fetch();
    }
}
