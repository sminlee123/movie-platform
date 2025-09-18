package com.example.movieplatform.genre.repository.impl;

import com.example.movieplatform.genre.domain.response.GenreResponse;
import com.example.movieplatform.genre.repository.CustomGenreRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.movieplatform.genre.domain.QGenre.genre;

@RequiredArgsConstructor
public class CustomGenreRepositoryImpl implements CustomGenreRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GenreResponse> findAllGenres(Pageable pageable) {
        List<GenreResponse> content = queryFactory
                .select(Projections.constructor(GenreResponse.class,
                        genre.id,
                        genre.name
                ))
                .from(genre)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(genre.id.desc())
                .fetch();

        Long total = queryFactory
                .select(genre.count())
                .from(genre)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }
}
