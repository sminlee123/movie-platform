package com.example.movieplatform.movie.domain.response;

public record SimpleMovieResponse (
        Long id,
        String title,
        String posterUrl
) {
}