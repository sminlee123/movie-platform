package com.example.movieplatform.movie.domain;

import com.example.movieplatform.client.domain.response.MovieResponseDto;
import com.example.movieplatform.moviegenre.domain.MovieGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    String docid;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String title_en;

    @Column(nullable = false)
    String director;

    @Column(nullable = false, columnDefinition = "TEXT")
    String plot;

    @Column(nullable = false, columnDefinition = "TEXT")
    String posterUrl;

    // 제작사
    @Column(nullable = false)
    String company;

    // 제작년도
    @Column(nullable = false, name = "release_date")
    String releaseDate;

    @Column(nullable = false)
    String runtime;

    @Column(nullable = false)
    String grade;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieGenre> movieGenres = new ArrayList<>();

    public static Movie fromDto(MovieResponseDto dto) {
        return new Movie(
                null,
                dto.docid(),
                dto.title(),
                dto.titleEng(),
                dto.directorNm(),
                dto.plot(),
                dto.posterUrl(),
                dto.company(),
                dto.releaseDate(),
                dto.runtime(),
                dto.ratingGrade(),
                new ArrayList<>()
        );
    }
}
