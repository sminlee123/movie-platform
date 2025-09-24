package com.example.movieplatform.reservation.domain.request;

import java.util.List;

public record ReservationRequest(
        Long showingInfoId,
        List<Long> seatIds
) {
}
