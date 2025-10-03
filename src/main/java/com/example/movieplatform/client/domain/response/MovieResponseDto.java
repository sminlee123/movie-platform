package com.example.movieplatform.client.domain.response;

import java.time.LocalDate;

public record MovieResponseDto(
        String docid,
        String title,
        String titleEng,
        String directorNm,
        String company,
        String plot,
        String posterUrl,
        String ratingGrade,
        LocalDate releaseDate,
        String runtime,
        String genre
) {
}