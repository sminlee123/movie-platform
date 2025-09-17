package com.example.movieplatform.movie.repository;

import com.example.movieplatform.movie.domain.response.MovieDetailResponse;
import com.example.movieplatform.movie.domain.response.SimpleMovieResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomMovieRepository {
    Page<SimpleMovieResponse> findAllMovies(Pageable pageable);
    Optional<MovieDetailResponse> getMovieDetailById(Long id);
}
