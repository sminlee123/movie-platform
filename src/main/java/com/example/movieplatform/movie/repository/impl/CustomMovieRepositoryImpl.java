package com.example.movieplatform.movie.repository.impl;

import com.example.movieplatform.movie.domain.Movie;
import com.example.movieplatform.movie.domain.response.MovieDetailResponse;
import com.example.movieplatform.movie.domain.response.SimpleMovieResponse;
import com.example.movieplatform.movie.repository.CustomMovieRepository;
import com.example.movieplatform.moviegenre.domain.MovieGenre;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.example.movieplatform.movie.domain.QMovie.movie;
import static com.example.movieplatform.moviegenre.domain.QMovieGenre.movieGenre;

@RequiredArgsConstructor
public class CustomMovieRepositoryImpl implements CustomMovieRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SimpleMovieResponse> findAllMovies(Pageable pageable) {
        List<SimpleMovieResponse> content = queryFactory
                .select(Projections.constructor(SimpleMovieResponse.class,
                        movie.id,
                        movie.title,
                        movie.posterUrl
                ))
                .from(movie)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(movie.releaseDate.desc())
                .fetch();

        Long total = queryFactory
                .select(movie.count())
                .from(movie)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    @Override
    public Optional<MovieDetailResponse> getMovieDetailById(Long id) {
        Movie useMovie = queryFactory
                .selectFrom(movie)
                .leftJoin(movie.movieGenres, movieGenre).fetchJoin()
                .where(movie.id.eq(id))
                .fetchOne();

        if (useMovie == null) {
            return Optional.empty();
        }

        MovieDetailResponse detail = convertToDto(useMovie);

        return Optional.ofNullable(detail);
    }

    private MovieDetailResponse convertToDto(Movie movie) {
        List<String> genreNames = movie.getMovieGenres()
                .stream()
                .map(MovieGenre::getGenreName)
                .toList();

        return new MovieDetailResponse(
                movie.getDocid(),
                movie.getTitle(),
                movie.getTitle_en(),
                movie.getDirector(),
                movie.getCompany(),
                movie.getPlot(),
                movie.getPosterUrl(),
                movie.getGrade(),
                movie.getReleaseDate().toString(),
                movie.getRuntime(),
                genreNames
        );

    }
}
