package com.example.movieplatform.ticket.repository;

import com.example.movieplatform.ticket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, CustomTicketRepository {
}
