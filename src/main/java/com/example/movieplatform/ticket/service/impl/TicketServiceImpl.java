package com.example.movieplatform.ticket.service.impl;

import com.example.movieplatform.ticket.domain.request.TicketCheckRequest;
import com.example.movieplatform.ticket.repository.TicketRepository;
import com.example.movieplatform.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public Long availableTicketCount(TicketCheckRequest request) {
        return 0L;
    }

    @Override
    public Long allTicketCount(TicketCheckRequest request) {
        return 0L;
    }
}
