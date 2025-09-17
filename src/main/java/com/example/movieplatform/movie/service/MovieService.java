package com.example.movieplatform.movie.service;

import com.example.movieplatform.client.domain.response.MovieResponseDto;
import com.example.movieplatform.movie.domain.response.MovieDetailResponse;
import com.example.movieplatform.movie.domain.response.SimpleMovieResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<SimpleMovieResponse> allMovies(Pageable pageable);
    void registerMovie(MovieResponseDto response);
    void deleteMovie(Long id);
    MovieDetailResponse getMovieDetailById(Long id);
}
