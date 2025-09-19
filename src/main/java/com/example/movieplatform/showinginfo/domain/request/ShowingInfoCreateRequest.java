package com.example.movieplatform.showinginfo.domain.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record ShowingInfoCreateRequest(
        Long movieId,
        Long screenId,
        LocalDate showingDate,
        LocalTime startTime,
        int price
) {
}