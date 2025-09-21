package com.example.movieplatform.ticket.service;

import com.example.movieplatform.ticket.domain.request.TicketBuyRequest;
import com.example.movieplatform.user.domain.User;

public interface TicketService {
    void ticketBuy(TicketBuyRequest request, User user);
}
