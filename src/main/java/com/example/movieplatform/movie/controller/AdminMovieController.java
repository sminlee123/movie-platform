package com.example.movieplatform.movie.controller;

import com.example.movieplatform.client.domain.response.MovieResponseDto;
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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin/movies")
@RequiredArgsConstructor
public class AdminMovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<Page<SimpleMovieResponse>> allMovies(@PageableDefault(size = 10, page = 1) Pageable pageable) {
        Page<SimpleMovieResponse> movies = movieService.allMovies(pageable);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public String getMovieDetail(@PathVariable Long id, Model model) {
        MovieDetailResponse detail = movieService.getMovieDetailById(id);
        model.addAttribute("movieDetail", detail);
        return "admin/movieDetail";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> movieRegister(@RequestBody MovieResponseDto response) {
        log.info("Received request to register movie: {}", response);
        movieService.registerMovie(response);
        return ResponseEntity.ok("영화가 성공적으로 등록되었습니다.");
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/admin/movies";
    }

}
