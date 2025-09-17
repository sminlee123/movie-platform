package com.example.movieplatform.genre.repository;

import com.example.movieplatform.genre.domain.response.GenreResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomGenreRepository {
    Page<GenreResponse> findAllGenres(Pageable pageable);
}
