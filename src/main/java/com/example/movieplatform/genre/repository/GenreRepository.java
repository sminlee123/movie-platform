package com.example.movieplatform.genre.repository;

import com.example.movieplatform.genre.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>, CustomGenreRepository {
    boolean existsByName(String name);
    Optional<Genre> findByName(String name);
    Optional<Genre> findById(Long id);
}
