package com.example.movieplatform.screen.service.impl;

import com.example.movieplatform.screen.domain.Screen;
import com.example.movieplatform.screen.domain.request.ScreenCreateRequest;
import com.example.movieplatform.screen.exception.ScreenAlreadyExistsException;
import com.example.movieplatform.screen.exception.ScreenNotFoundException;
import com.example.movieplatform.screen.repository.ScreenRepository;
import com.example.movieplatform.screen.service.ScreenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;

    @Override
    public void createScreen(ScreenCreateRequest request) {
        if(screenRepository.existsByName(request.name())){
            throw new ScreenAlreadyExistsException();
        }
        Screen screen = new Screen(request.name());
        screenRepository.save(screen);
        log.info("Created Screen with name {}", screen.getName());
    }

    @Override
    public void deleteScreen(Long screenId) {
        if(!screenRepository.existsById(screenId)){
            throw new ScreenNotFoundException();
        }
        screenRepository.deleteById(screenId);
        log.info("Deleted Screen with id {}", screenId);
    }

    @Override
    public List<Screen> getAllScreens() {
        return screenRepository.findAll();
    }

    @Override
    public Screen getScreenById(Long id) {
        return screenRepository.findById(id)
                .orElseThrow(ScreenNotFoundException::new);
    }
}
