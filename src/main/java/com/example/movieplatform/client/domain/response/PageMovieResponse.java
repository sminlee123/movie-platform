package com.example.movieplatform.client.domain.response;

import java.util.List;

public record PageMovieResponse (
        long totalElements,
        List<MovieResponseDto> content
){
}
