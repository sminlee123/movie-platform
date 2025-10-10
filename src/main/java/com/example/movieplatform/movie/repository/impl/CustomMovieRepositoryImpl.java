package com.example.movieplatform.movie.repository.impl;

import com.example.movieplatform.movie.domain.Movie;
import com.example.movieplatform.movie.domain.response.MovieDetailResponse;
import com.example.movieplatform.movie.domain.response.SimpleMovieResponse;
import com.example.movieplatform.movie.repository.CustomMovieRepository;
import com.example.movieplatform.moviegenre.domain.MovieGenre;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.movieplatform.movie.domain.QMovie.movie;
import static com.example.movieplatform.moviegenre.domain.QMovieGenre.movieGenre;

@Slf4j
@RequiredArgsConstructor
public class CustomMovieRepositoryImpl implements CustomMovieRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SimpleMovieResponse> findAllMovies(Pageable pageable) {
        List<Sort.Order> newOrders = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            String keyword = order.getProperty();

            switch (keyword) {
                case "oldest":
                    newOrders.add(Sort.Order.asc("releaseDate"));
                    break;
                case "title":
                    newOrders.add(Sort.Order.desc("title"));
                    break;
                case "latest":
                default:
                    newOrders.add(Sort.Order.desc("releaseDate"));
                    break;
            }
        }

        // 정렬 조건이 안들어왔을때 기본정렬 추가
        if (newOrders.isEmpty()) {
            newOrders.add(Sort.Order.desc("releaseDate"));
        }

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (Sort.Order order : newOrders) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            PathBuilder<Movie> pathBuilder = new PathBuilder<>(Movie.class, "movie");
            orderSpecifiers.add(new OrderSpecifier(direction, pathBuilder.get(property)));
        }

        log.info("OrderSpecifiers: {}", orderSpecifiers);

        List<SimpleMovieResponse> content = queryFactory
                .select(Projections.constructor(SimpleMovieResponse.class,
                        movie.id,
                        movie.title,
                        movie.posterUrl
                ))
                .from(movie)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
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
