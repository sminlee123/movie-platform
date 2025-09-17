package com.example.movieplatform.movie.repository;

import com.example.movieplatform.movie.domain.response.SimpleMovieResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomMovieRepository {
    Page<SimpleMovieResponse> findAllMovies(Pageable pageable);
}
