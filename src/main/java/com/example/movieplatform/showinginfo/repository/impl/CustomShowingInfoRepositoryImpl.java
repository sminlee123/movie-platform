package com.example.movieplatform.showinginfo.repository.impl;

import com.example.movieplatform.showinginfo.domain.response.ShowingInfoResponse;
import com.example.movieplatform.showinginfo.repository.CustomShowingInfoRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.example.movieplatform.movie.domain.QMovie.movie;
import static com.example.movieplatform.screen.domain.QScreen.screen;
import static com.example.movieplatform.showinginfo.domain.QShowingInfo.showingInfo;

@RequiredArgsConstructor
public class CustomShowingInfoRepositoryImpl implements CustomShowingInfoRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ShowingInfoResponse> getShowingInfoByShowingId(Long showingId) {
        ShowingInfoResponse response = queryFactory
                .select(Projections.constructor(ShowingInfoResponse.class,
                        showingInfo.id,
                        movie.title,
                        screen.name,
                        showingInfo.showingDate,
                        showingInfo.startTime,
                        showingInfo.endTime,
                        showingInfo.price
                ))
                .from(showingInfo)
                .join(showingInfo.movie, movie)
                .join(showingInfo.screen, screen)
                .where(showingInfo.id.eq(showingId))
                .fetchOne();

        return Optional.ofNullable(response);
    }

    @Override
    public Page<ShowingInfoResponse> findAllShowingsByScreenId(Pageable pageable, Long screenId) {
        List<ShowingInfoResponse> content = queryFactory
                .select(Projections.constructor(ShowingInfoResponse.class,
                        showingInfo.id,
                        movie.title,
                        screen.name,
                        showingInfo.showingDate,
                        showingInfo.startTime,
                        showingInfo.endTime,
                        showingInfo.price
                ))
                .from(showingInfo)
                .join(showingInfo.movie, movie)
                .join(showingInfo.screen, screen)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(showingInfo.screen.id.eq(screenId))
                .orderBy(showingInfo.id.desc())
                .fetch();

        Long total = queryFactory
                .select(showingInfo.count())
                .from(showingInfo)
                .where(showingInfo.screen.id.eq(screenId))
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }
}
