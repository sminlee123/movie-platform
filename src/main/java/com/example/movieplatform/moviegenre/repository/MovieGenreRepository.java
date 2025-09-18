package com.example.movieplatform.moviegenre.repository;

import com.example.movieplatform.genre.domain.Genre;
import com.example.movieplatform.movie.domain.Movie;
import com.example.movieplatform.moviegenre.domain.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {
    Optional<MovieGenre> findByMovieAndGenre(Movie movie, Genre genre);
}
