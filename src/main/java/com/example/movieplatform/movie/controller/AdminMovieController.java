package com.example.movieplatform.movie.controller;

import com.example.movieplatform.client.domain.response.MovieResponseDto;
import com.example.movieplatform.movie.domain.response.SimpleMovieResponse;
import com.example.movieplatform.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String movieRegister(@RequestBody MovieResponseDto response) {
        log.info("Received request to register movie: {}", response);
        movieService.registerMovie(response);
        return "redirect:/admin/movies";
    }

}
