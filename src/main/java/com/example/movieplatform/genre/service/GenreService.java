package com.example.movieplatform.genre.service;

import com.example.movieplatform.genre.domain.request.GenreCreateRequest;
import com.example.movieplatform.genre.domain.response.GenreResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreService {
    void createGenre(GenreCreateRequest request);
    void deleteGenre(Long id);
    Page<GenreResponse> allGenres(Pageable pageable);
}
