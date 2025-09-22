package com.example.movieplatform.showinginfo.service.impl;

import com.example.movieplatform.movie.domain.Movie;
import com.example.movieplatform.movie.exception.MovieNotExistsException;
import com.example.movieplatform.movie.repository.MovieRepository;
import com.example.movieplatform.reservation.exception.TimeAfterException;
import com.example.movieplatform.screen.domain.Screen;
import com.example.movieplatform.screen.exception.ScreenNotFoundException;
import com.example.movieplatform.screen.repository.ScreenRepository;
import com.example.movieplatform.screen.service.SeatService;
import com.example.movieplatform.showinginfo.domain.ShowingInfo;
import com.example.movieplatform.showinginfo.domain.request.ShowingInfoCreateRequest;
import com.example.movieplatform.showinginfo.domain.response.ShowingInfoResponse;
import com.example.movieplatform.showinginfo.exception.ShowingDateException;
import com.example.movieplatform.showinginfo.exception.ShowingInfoAlreadyExistsException;
import com.example.movieplatform.showinginfo.exception.ShowingInfoNotExistsException;
import com.example.movieplatform.showinginfo.repository.ShowingInfoRepository;
import com.example.movieplatform.showinginfo.service.ShowingInfoService;
import com.example.movieplatform.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ShowingInfoServiceImpl implements ShowingInfoService {

    private final ShowingInfoRepository showingInfoRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final SeatService seatService;
    private final TicketRepository ticketRepository;

    @Override
    public Long createShowingInfo(ShowingInfoCreateRequest request) {
        if (request.showingDate().isBefore(LocalDate.now())) {
            throw new ShowingDateException();
        }

        Movie movie = movieRepository.findByid(request.movieId())
                .orElseThrow(MovieNotExistsException::new);

        Screen screen = screenRepository.findById(request.screenId())
                .orElseThrow(ScreenNotFoundException::new);

        if (showingInfoRepository.existsByScreenAndShowingDate(screen, request.showingDate())) {
            throw new ShowingInfoAlreadyExistsException();
        }

        long runtime = Long.parseLong(movie.getRuntime());
        LocalTime startTime = request.startTime();
        LocalTime endTime = startTime.plusMinutes(runtime).plusMinutes(10);

        ShowingInfo showingInfo = ShowingInfo.create(request, movie, screen, endTime);
        showingInfoRepository.save(showingInfo);

        log.info("Create ShowingInfo: {}", request);

        return request.screenId();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShowingInfoResponse> getShowingInfos(Pageable pageable, Long screenId) {
        Page<ShowingInfoResponse> page = showingInfoRepository.findAllShowingsByScreenId(pageable, screenId);
        List<ShowingInfoResponse> content = page.getContent();

        if (content.isEmpty()) {
            return page; // 내용이 없으면 바로 반환
        }

        // 해당 스크린의 모든 좌석
        Long totalSeats = seatService.countAllSeats(screenId);

        List<Long> showingInfoIds = content.stream()
                .map(ShowingInfoResponse::getId)
                .toList();

        // 상영정보 각각의 예매된 좌석
        Map<Long, Long> bookedCountsMap = ticketRepository.findBookedCountsByShowingInfoIds(showingInfoIds);

        content.forEach(dto -> {
            long bookedSeats = bookedCountsMap.getOrDefault(dto.getId(), 0L);
            dto.setSeatCounts(totalSeats, bookedSeats);
        });

        return page;
    }

    @Override
    public Long deleteShowingInfo(Long showingInfoId) {
        ShowingInfo showingInfo = showingInfoRepository.findById(showingInfoId)
                .orElseThrow(ShowingInfoNotExistsException::new);
        Long screenId = showingInfo.getScreen().getId();
        showingInfoRepository.delete(showingInfo);
        log.info("Delete ShowingInfo: {}", showingInfoId);
        return screenId;
    }

    @Override
    public ShowingInfo validateShowingInfo(Long showingInfoId) {
        ShowingInfo showinginfo = showingInfoRepository.findById(showingInfoId)
                .orElseThrow(ShowingInfoNotExistsException::new);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.of(showinginfo.getShowingDate(), showinginfo.getStartTime());

        if(now.isAfter(startTime)) {
            throw new TimeAfterException(); // TODO 이름 고민하기
        }

        return showinginfo;
    }
}
