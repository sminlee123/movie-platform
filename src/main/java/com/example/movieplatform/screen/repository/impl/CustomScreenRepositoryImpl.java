package com.example.movieplatform.screen.repository.impl;

import com.example.movieplatform.screen.domain.response.ScreenResponse;
import com.example.movieplatform.screen.repository.CustomScreenRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.example.movieplatform.screen.domain.QScreen.screen;

@RequiredArgsConstructor
public class CustomScreenRepositoryImpl implements CustomScreenRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ScreenResponse> findAllScreens(Pageable pageable) {
        List<ScreenResponse> content = queryFactory
                .select(Projections.constructor(ScreenResponse.class,
                        screen.id,
                        screen.name
                ))
                .from(screen)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(screen.id.asc())
                .fetch();

        Long total = queryFactory
                .select(screen.count())
                .from(screen)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    @Override
    public Optional<ScreenResponse> responseFindById(Long id) {
        ScreenResponse response = queryFactory
                .select(Projections.constructor(ScreenResponse.class,
                        screen.id,
                        screen.name
                ))
                .from(screen)
                .where(screen.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(response);
    }
}
