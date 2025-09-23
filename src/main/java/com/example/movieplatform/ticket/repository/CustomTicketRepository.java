package com.example.movieplatform.ticket.repository;

import java.util.List;
import java.util.Map;

public interface CustomTicketRepository {
    Map<Long, Long> findBookedCountsByShowingInfoIds(List<Long> showingInfoIds);
    List<String> findSeatNameByReservationId(Long reservationId);
}
