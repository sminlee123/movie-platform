package com.example.movieplatform.ticket.service;

import com.example.movieplatform.ticket.domain.request.TicketCheckRequest;

public interface TicketService {
    Long availableTicketCount(TicketCheckRequest request);
    Long allTicketCount(TicketCheckRequest request);
}
