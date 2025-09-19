package com.example.movieplatform.showinginfo.domain.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record ShowingInfoResponse(
        Long id,
        String movieName,
        String screenName,
        LocalDate showingDate,
        LocalTime startTime,
        LocalTime endTime,
        int price
) {
}
