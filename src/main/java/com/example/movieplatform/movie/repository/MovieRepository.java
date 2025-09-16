package com.example.movieplatform.movie.repository;

import com.example.movieplatform.movie.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, CustomMovieRepository {
    Page<Movie> findAll(Pageable pageable);
    boolean existsByDocid(String docid);
}
