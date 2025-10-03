package com.example.movieplatform.movie.controller;

import com.example.movieplatform.movie.domain.response.MovieDetailResponse;
import com.example.movieplatform.movie.domain.response.SimpleMovieResponse;
import com.example.movieplatform.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<Page<SimpleMovieResponse>> allMovies(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<SimpleMovieResponse> movies = movieService.allMovies(pageable);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailResponse> getMovieDetail(@PathVariable Long id) {
        MovieDetailResponse detail = movieService.getMovieDetailById(id);
        return ResponseEntity.ok(detail);
    }
}
