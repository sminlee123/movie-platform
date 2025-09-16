package com.example.movieplatform.movie.controller;

import com.example.movieplatform.client.domain.response.MovieResponseDto;
import com.example.movieplatform.movie.domain.response.SimpleMovieResponse;
import com.example.movieplatform.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/admin/movies")
@RequiredArgsConstructor
public class AdminMovieController {

    private final MovieService movieService;

    @GetMapping
    public String allMovies(@PageableDefault(size = 10, page = 0) Pageable pageable,
                            Model model) {
        Page<SimpleMovieResponse> movies = movieService.allMovies(pageable);
        model.addAttribute("movies", movies);
        return "admin/movies";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> movieRegister(@RequestBody MovieResponseDto response) {
        try {
            log.info("Received request to register movie: {}", response);
            movieService.registerMovie(response);
            return ResponseEntity.ok("영화가 성공적으로 등록되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류로 등록에 실패했습니다.");
        }
    }

}
