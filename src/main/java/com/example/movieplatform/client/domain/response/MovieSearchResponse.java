package com.example.movieplatform.client.domain.response;

public record MovieSearchResponse (
        String title,

        String titleEng,

        String directorNm,

        String company,

        String releaseDate,

        String plot,

        String runtime,

        String posterUrl

) {
}
