package com.example.movieplatform.ticket.repository;

import com.example.movieplatform.reservation.domain.Reservation;
import com.example.movieplatform.screen.domain.Seat;
import com.example.movieplatform.showinginfo.domain.ShowingInfo;
import com.example.movieplatform.ticket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, CustomTicketRepository {
    boolean existsByShowingInfoAndSeatIn(ShowingInfo showingInfo, List<Seat> seats);
    Optional<Ticket> findFirstByReservation(Reservation reservation);
}
