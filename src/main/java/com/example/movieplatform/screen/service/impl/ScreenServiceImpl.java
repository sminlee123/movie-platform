package com.example.movieplatform.screen.service.impl;

import com.example.movieplatform.screen.domain.Screen;
import com.example.movieplatform.screen.domain.request.ScreenCreateRequest;
import com.example.movieplatform.screen.domain.response.ScreenResponse;
import com.example.movieplatform.screen.exception.ScreenAlreadyExistsException;
import com.example.movieplatform.screen.exception.ScreenNotFoundException;
import com.example.movieplatform.screen.repository.ScreenRepository;
import com.example.movieplatform.screen.repository.SeatRepository;
import com.example.movieplatform.screen.service.ScreenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;

    @Override
    public Long createScreen(ScreenCreateRequest request) {
        if(screenRepository.existsByName(request.name())){
            throw new ScreenAlreadyExistsException();
        }
        Screen screen = new Screen(request.name());
        Screen savedScreen = screenRepository.save(screen);
        log.info("Created Screen with name {}", screen.getName());
        return savedScreen.getId();
    }

    @Override
    public void deleteScreen(Long screenId) {
        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(ScreenNotFoundException::new);
        seatRepository.deleteAllByScreen(screen);
        screenRepository.deleteById(screenId);
        log.info("Delete Screen with id {}", screenId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScreenResponse> getAllScreens(Pageable pageable) {
        return screenRepository.findAllScreens(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public ScreenResponse getScreenById(Long id) {
        return screenRepository.responseFindById(id)
                .orElseThrow(ScreenNotFoundException::new);
    }
}
