package com.example.movieplatform.showinginfo.controller;

import com.example.movieplatform.showinginfo.domain.response.ShowingInfoResponse;
import com.example.movieplatform.showinginfo.domain.response.ShowingSeatsResponse;
import com.example.movieplatform.showinginfo.service.ShowingInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/showings")
@RequiredArgsConstructor
public class ShowingInfoController {

    private final ShowingInfoService showingInfoService;

    @GetMapping("/{movieId}")
    public ResponseEntity<List<ShowingInfoResponse>> getShowingInfosByMovieIdAndThreeDays
            (@PathVariable Long movieId) {
        List<ShowingInfoResponse> showingInfos =  showingInfoService.getShowingInfosByMovieId(movieId);
        return ResponseEntity.ok(showingInfos);
    }

    @GetMapping("/{showingInfoId}/seats")
    public ResponseEntity<ShowingSeatsResponse> getShowingSeats
            (@PathVariable Long showingInfoId) {
        ShowingSeatsResponse seatsResponse = showingInfoService.getShowingSeats(showingInfoId);
        return ResponseEntity.ok(seatsResponse);
    }
}
