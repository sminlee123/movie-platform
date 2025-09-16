package com.example.movieplatform.client.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MovieApiResponse(
        @JsonProperty("TotalCount")
        int totalCount,

        @JsonProperty("Data")
        List<MovieData> data
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MovieData(
            @JsonProperty("Result")
            List<MovieResult> result
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MovieResult(
            @JsonProperty("DOCID")
            String docid,

            @JsonProperty("title")
            String title,

            @JsonProperty("titleEng")
            String titleEng,

            @JsonProperty("directors")
            Directors directors,

            @JsonProperty("company")
            String company,

            @JsonProperty("plots")
            Plots plots,

            @JsonProperty("posters")
            String posterUrl,

            @JsonProperty("ratings")
            Ratings ratings,

            @JsonProperty("genre")
            String genre
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Directors(
            @JsonProperty("director")
            List<Director> director
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Director(
            @JsonProperty("directorNm")
            String directorNm
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Plots(
            @JsonProperty("plot")
            List<Plot> plot
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Plot(
            @JsonProperty("plotText")
            String plotText
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Ratings(
            @JsonProperty("rating")
            List<Rating> rating
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Rating(
            @JsonProperty("ratingGrade")
            String ratingGrade,
            @JsonProperty("releaseDate")
            String releaseDate,
            @JsonProperty("runtime")
            String runtime
    ) {}
}
