package com.example.movieplatform.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "movie-search-service",
        url = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api")
public interface MovieSearchClient {

    // 파라미터들 넣기
    @GetMapping("/search_json2.jsp")
    String movieSearch(
            @RequestParam("ServiceKey") String apiKey,
            @RequestParam("query") String query // 검색 쿼리
    );
}
