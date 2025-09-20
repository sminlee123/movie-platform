package com.example.movieplatform.showinginfo.domain.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ShowingInfoResponse {

    private final Long id;
    private final String movieName;
    private final String screenName;
    private final LocalDate showingDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final int price;

    private Long totalSeatCount;
    private Long bookSeatCount;
    private Long availableSeatCount;

    public ShowingInfoResponse(Long id, String movieName, String screenName,
                               LocalDate showingDate, LocalTime startTime,
                               LocalTime endTime, int price) {
        this.id = id;
        this.movieName = movieName;
        this.screenName = screenName;
        this.showingDate = showingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public void setSeatCounts(long totalSeatCount, long bookedSeatCount) {
        this.totalSeatCount = totalSeatCount;
        this.bookSeatCount = bookedSeatCount;
        this.availableSeatCount = totalSeatCount - bookedSeatCount;
    }
}