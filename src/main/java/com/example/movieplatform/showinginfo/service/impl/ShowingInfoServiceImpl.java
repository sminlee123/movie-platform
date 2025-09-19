package com.example.movieplatform.showinginfo.service.impl;

import com.example.movieplatform.movie.domain.Movie;
import com.example.movieplatform.movie.exception.MovieNotExistsException;
import com.example.movieplatform.movie.repository.MovieRepository;
import com.example.movieplatform.screen.domain.Screen;
import com.example.movieplatform.screen.exception.ScreenNotFoundException;
import com.example.movieplatform.screen.repository.ScreenRepository;
import com.example.movieplatform.showinginfo.domain.ShowingInfo;
import com.example.movieplatform.showinginfo.domain.request.ShowingInfoCreateRequest;
import com.example.movieplatform.showinginfo.domain.response.ShowingInfoResponse;
import com.example.movieplatform.showinginfo.exception.ShowingInfoNotExistsException;
import com.example.movieplatform.showinginfo.repository.ShowingInfoRepository;
import com.example.movieplatform.showinginfo.service.ShowingInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ShowingInfoServiceImpl implements ShowingInfoService {

    private final ShowingInfoRepository showingInfoRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;

    @Override
    public void createShowingInfo(ShowingInfoCreateRequest request) {
        Movie movie = movieRepository.findByid(request.movieId())
                .orElseThrow(MovieNotExistsException::new);

        Screen screen = screenRepository.findById(request.screenId())
                .orElseThrow(ScreenNotFoundException::new);

        long runtime = Long.parseLong(movie.getRuntime());
        LocalTime startTime = request.startTime();
        LocalTime endTime = startTime.plusMinutes(runtime).plusMinutes(10);

        ShowingInfo showingInfo = ShowingInfo.create(request, movie, screen, endTime);
        showingInfoRepository.save(showingInfo);

        log.info("Create ShowingInfo: {}", request);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShowingInfoResponse> getShowingInfos(Pageable pageable, Long screenId) {
        return showingInfoRepository.findAllShowingsByScreenId(pageable, screenId);
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
}
