package com.example.movieplatform.ticket.service;

import com.example.movieplatform.ticket.domain.request.TicketBuyRequest;

public interface TicketService {
    void ticketBuy(TicketBuyRequest request);
}
