package com.example.movieplatform.client.service;

import com.example.movieplatform.client.MovieSearchClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieSearchService {

    private final MovieSearchClient movieSearchClient;

    private final String API_KEY = "";

    public String searchMovie() {
        return movieSearchClient.movieSearch(API_KEY, "공포");
    }
}
