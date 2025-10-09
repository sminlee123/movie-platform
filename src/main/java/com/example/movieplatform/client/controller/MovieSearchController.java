package com.example.movieplatform.client.controller;

import com.example.movieplatform.client.domain.response.MovieResponseDto;
import com.example.movieplatform.client.service.MovieSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MovieSearchController {

    private final MovieSearchService movieSearchService;

    @GetMapping("/api/movie-search")
    public ResponseEntity<List<MovieResponseDto>> getMovies(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) throws JsonProcessingException {
        List<MovieResponseDto> movies = movieSearchService.searchMovie(query, page);
        return ResponseEntity.ok(movies);
    }
}
