package com.example.movieplatform.showinginfo.domain.response;

import java.util.List;

public record ShowingSeatsResponse(
        Long screenId,
        String screenName,
        int totalRows,
        int totalColumns,
        List<SeatInfo> seats
) {
    public record SeatInfo(
            Long seatId,
            int row,
            int column,
            SeatStatus status
    ) {}

    public enum SeatStatus {
        AVAILABLE,
        NOT_AVAILABLE,
    }
}