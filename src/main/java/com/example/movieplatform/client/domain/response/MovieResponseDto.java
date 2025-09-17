package com.example.movieplatform.client.domain.response;

public record MovieResponseDto(
        String docid,
        String title,
        String titleEng,
        String directorNm,
        String company,
        String plot,
        String posterUrl,
        String ratingGrade,
        String releaseDate,
        String runtime,
        String genre
) {
}