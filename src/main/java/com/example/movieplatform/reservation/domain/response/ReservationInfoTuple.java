package com.example.movieplatform.reservation.domain.response;

import com.example.movieplatform.reservation.domain.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationInfoTuple(
        Long reservationId,
        ReservationStatus status,
        LocalDateTime reservationDate,
        int finalPrice,
        String movieTitle,
        String posterUrl,
        String screenName,
        LocalDate showingDate,
        LocalTime startTime,
        LocalTime endTime
) {}
