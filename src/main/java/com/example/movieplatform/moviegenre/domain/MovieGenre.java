package com.example.movieplatform.moviegenre.domain;

import com.example.movieplatform.genre.domain.Genre;
import com.example.movieplatform.movie.domain.Movie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "movie_genres")
@NoArgsConstructor
@AllArgsConstructor
public class MovieGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    private MovieGenre(Movie movie, Genre genre) {
        this.movie = movie;
        this.genre = genre;
    }

    public static MovieGenre valueOf(Movie movie, Genre genre) {
        return new MovieGenre(movie, genre);
    }

    public String getGenreName() {
        return genre.getName();
    }

}