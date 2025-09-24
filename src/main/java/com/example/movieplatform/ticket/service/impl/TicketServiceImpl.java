package com.example.movieplatform.ticket.service.impl;

import com.example.movieplatform.reservation.domain.Reservation;
import com.example.movieplatform.screen.domain.Seat;
import com.example.movieplatform.showinginfo.domain.ShowingInfo;
import com.example.movieplatform.ticket.domain.Ticket;
import com.example.movieplatform.ticket.repository.TicketRepository;
import com.example.movieplatform.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public void createAndAddTicketsToReservation(
            Reservation reservation,
            ShowingInfo showingInfo,
            List<Seat> seats) {
        List<Ticket> tickets = seats.stream()
                .map(seat -> Ticket.create(showingInfo, seat, reservation))
                .toList();

        ticketRepository.saveAll(tickets);
    }
}
