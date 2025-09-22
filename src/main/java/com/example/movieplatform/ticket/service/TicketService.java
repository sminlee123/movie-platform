package com.example.movieplatform.ticket.service;

import com.example.movieplatform.reservation.domain.Reservation;
import com.example.movieplatform.screen.domain.Seat;
import com.example.movieplatform.showinginfo.domain.ShowingInfo;

import java.util.List;

public interface TicketService {
    void createAndAddTicketsToReservation(Reservation reservation, ShowingInfo showingInfo, List<Seat> seats);
}
