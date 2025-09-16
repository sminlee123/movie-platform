package com.example.movieplatform.movie.repository.impl;

import com.example.movieplatform.movie.domain.Movie;
import com.example.movieplatform.movie.domain.response.SimpleMovieResponse;
import com.example.movieplatform.movie.repository.CustomMovieRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.movieplatform.movie.domain.QMovie.movie;

@RequiredArgsConstructor
public class CustomMovieRepositoryImpl implements CustomMovieRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SimpleMovieResponse> findAllMovies(Pageable pageable) {

        List<SimpleMovieResponse> content = queryFactory
                .select(Projections.constructor(SimpleMovieResponse.class,
                        movie.title
                ))
                .from(movie)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(movie.id.desc()) // TODO 정렬 조건 추가
                .fetch();

        Long total = queryFactory
                .select(movie.count())
                .from(movie)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }
}
