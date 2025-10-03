package com.example.movieplatform.movie.domain.response;

import java.util.List;

public record MovieDetailResponse (
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
        List<String> genres
) {
}
