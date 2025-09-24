package com.example.movieplatform.ticket.repository;

import com.example.movieplatform.screen.domain.Seat;
import com.example.movieplatform.showinginfo.domain.ShowingInfo;

import java.util.List;
import java.util.Map;

public interface CustomTicketRepository {
    Map<Long, Long> findBookedCountsByShowingInfoIds(List<Long> showingInfoIds);
    List<String> findSeatNameByReservationId(Long reservationId);
    boolean validateTicketForReservation(ShowingInfo showingInfo, List<Seat> seats);
}
