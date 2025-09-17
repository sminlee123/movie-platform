package com.example.movieplatform.client.service;

import com.example.movieplatform.client.MovieSearchClient;
import com.example.movieplatform.client.domain.response.MovieApiResponse;
import com.example.movieplatform.client.domain.response.MovieResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieSearchService {

    private final MovieSearchClient movieSearchClient;
    private final ObjectMapper objectMapper;

    @Value("${myapi.key}")
    private String apiKey;

    private final String COLLECTION = "kmdb_new2";
    private final int DEFAULT_LIST_COUNT = 10;

    public List<MovieResponseDto> searchMovie(String query, int pageNumber) throws JsonProcessingException {
        int startCount = (pageNumber - 1) * DEFAULT_LIST_COUNT;
        String apiResponse = movieSearchClient.movieSearch(
                apiKey, COLLECTION, query, startCount, DEFAULT_LIST_COUNT);

        log.info("API Response: {}", apiResponse);

        MovieApiResponse response = objectMapper.readValue(apiResponse, MovieApiResponse.class);

        log.info("Movie Search Response: {}", objectMapper.writeValueAsString(response));


        if (response == null || response.data() == null || response.data().isEmpty() || response.data().get(0).result() == null) {
            return Collections.emptyList();
        }

        return response.data().get(0).result().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MovieResponseDto convertToDto(MovieApiResponse.MovieResult movieResult) {
        String cleanTitle = movieResult.title().replace("!HS", "").replace("!HE", "").trim();

        String directorName = "";
        if (movieResult.directors() != null && movieResult.directors().director() != null && !movieResult.directors().director().isEmpty()) {
            directorName = movieResult.directors().director().get(0).directorNm();
        }

        String plotText = "";
        if (movieResult.plots() != null && movieResult.plots().plot() != null && !movieResult.plots().plot().isEmpty()) {
            plotText = movieResult.plots().plot().get(0).plotText();
        }

        // 포스터 URL 처리
        String posterUrl = movieResult.posterUrl();
        String finalPosterUrl = "";
        if (posterUrl != null && !posterUrl.isEmpty()) {
            finalPosterUrl = posterUrl.split("\\|")[0].trim();
        }

        String ratingGrade = "";
        String releaseDate = "";
        String runtime = "";
        if (movieResult.ratings() != null && movieResult.ratings().rating() != null && !movieResult.ratings().rating().isEmpty()) {
            MovieApiResponse.Rating ratings = movieResult.ratings().rating().get(0);
            ratingGrade = ratings.ratingGrade();
            releaseDate = ratings.releaseDate();
            runtime = ratings.runtime();
        }
        return new MovieResponseDto(
                movieResult.docid(),
                cleanTitle,
                movieResult.titleEng(),
                directorName,
                movieResult.company(),
                plotText,
                finalPosterUrl,
                ratingGrade,
                releaseDate,
                runtime,
                movieResult.genre()
        );
    }
}