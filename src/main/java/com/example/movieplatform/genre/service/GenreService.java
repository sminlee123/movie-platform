package com.example.movieplatform.genre.service;

import com.example.movieplatform.genre.domain.request.GenreCreateRequest;
import com.example.movieplatform.genre.domain.response.GenreResponse;

import java.util.List;

public interface GenreService {
    void createGenre(GenreCreateRequest request);
    void deleteGenre(Long id);
    List<GenreResponse> listGenres();
}
