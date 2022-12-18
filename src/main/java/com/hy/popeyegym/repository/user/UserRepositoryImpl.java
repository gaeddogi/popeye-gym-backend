package com.hy.popeyegym.repository.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.hy.popeyegym.domain.user.QUser.user;
import static com.hy.popeyegym.dto.response.UserResponseDto.GetUserAllRes;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GetUserAllRes> getUserAll(String emailParam, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (emailParam != null) {
            builder.and(user.email.contains(emailParam));
        }

        List<GetUserAllRes> contents = queryFactory
                .select(Projections.fields(GetUserAllRes.class,
                        user.id,
                        user.email
                ))
                .from(user)
                .where(builder)
                .limit(pageable.getPageSize())
                .offset(Math.max(pageable.getOffset(), 0))
                .orderBy(user.email.asc())
                .fetch();

        Long total = queryFactory
                .select(user.count())
                .from(user)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }
}
