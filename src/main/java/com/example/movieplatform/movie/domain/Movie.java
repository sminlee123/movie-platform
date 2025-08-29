package com.example.movieplatform.movie.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String title_en;

    @Column(nullable = false)
    String director;

    @Column(nullable = false, columnDefinition = "TEXT")
    String plot;

    @Column(nullable = false, columnDefinition = "TEXT")
    String image;

    // 제작사
    @Column(nullable = false)
    String company;

    // 제작년도
    @Column(nullable = false, name = "release_date")
    LocalDateTime releaseDate;

    @Column(nullable = false)
    String runtime;
}
