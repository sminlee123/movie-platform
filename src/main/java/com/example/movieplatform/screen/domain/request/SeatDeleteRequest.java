package com.example.movieplatform.screen.domain.request;

public record SeatDeleteRequest(
        Long screenId,
        String seatName
) {
}