package com.example.movieplatform.screen.service;

import com.example.movieplatform.screen.domain.request.ScreenCreateRequest;
import com.example.movieplatform.screen.domain.response.ScreenResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScreenService {
    Long createScreen(ScreenCreateRequest request);
    void deleteScreen(Long screenId);
    Page<ScreenResponse> getAllScreens(Pageable pageable);
    ScreenResponse getScreenById(Long id);
}
