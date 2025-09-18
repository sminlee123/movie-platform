package com.example.movieplatform.screen.service;

import com.example.movieplatform.screen.domain.Screen;
import com.example.movieplatform.screen.domain.request.ScreenCreateRequest;

import java.util.List;

public interface ScreenService {
    void createScreen(ScreenCreateRequest request);
    void deleteScreen(Long screenId);
    List<Screen> getAllScreens();
    Screen getScreenById(Long id);
}
