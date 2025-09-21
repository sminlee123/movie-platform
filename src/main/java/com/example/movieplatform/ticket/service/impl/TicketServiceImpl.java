package com.example.movieplatform.ticket.service.impl;

import com.example.movieplatform.screen.domain.Seat;
import com.example.movieplatform.screen.exception.SeatNotAvailableException;
import com.example.movieplatform.screen.exception.SeatNotFoundException;
import com.example.movieplatform.screen.repository.SeatRepository;
import com.example.movieplatform.showinginfo.domain.ShowingInfo;
import com.example.movieplatform.showinginfo.exception.ShowingInfoNotExistsException;
import com.example.movieplatform.showinginfo.repository.ShowingInfoRepository;
import com.example.movieplatform.ticket.domain.Ticket;
import com.example.movieplatform.ticket.domain.request.TicketBuyRequest;
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
    private final ShowingInfoRepository showingInfoRepository;
    private final SeatRepository seatRepository;

    @Override
    public void ticketBuy(TicketBuyRequest request) {
        ShowingInfo showingInfo = showingInfoRepository.findById(request.showingInfoId())
                .orElseThrow(ShowingInfoNotExistsException::new);

        Seat seat = seatRepository.findByNameAndScreen(request.seatName(), showingInfo.getScreen())
                .orElseThrow(SeatNotFoundException::new);

        if (ticketRepository.existsByShowingInfoAndSeat(showingInfo, seat)) {
            throw new SeatNotAvailableException();
        }

        Ticket ticket = new Ticket(showingInfo, seat);
        ticketRepository.save(ticket);
        log.info("Ticket has been built : {}", ticket.getId());
    }
}
