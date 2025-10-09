package com.example.movieplatform.screen.controller;

import com.example.movieplatform.screen.domain.request.ScreenCreateRequest;
import com.example.movieplatform.screen.domain.response.ScreenResponse;
import com.example.movieplatform.screen.service.ScreenService;
import com.example.movieplatform.showinginfo.domain.response.ShowingInfoResponse;
import com.example.movieplatform.showinginfo.service.ShowingInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin/screens")
@RequiredArgsConstructor
public class AdminScreenController {

    private final ScreenService screenService;
    private final ShowingInfoService showingInfoService;

    @GetMapping
    public ResponseEntity<Page<ScreenResponse>> screenPage(@PageableDefault(size = 10, page = 1) Pageable pageable) {
        Page<ScreenResponse> screenList = screenService.getAllScreens(pageable);
        return ResponseEntity.ok(screenList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<ShowingInfoResponse>> screenDetail(@PageableDefault(size = 10, page = 1) Pageable pageable,
                               @PathVariable Long id) {
        Page<ShowingInfoResponse> showingInfo = showingInfoService.getShowingInfos(pageable, id);
        return ResponseEntity.ok(showingInfo);
    }

    @GetMapping("/create")
    public String createForm(){
        return "admin/screenCreate";
    }

    @PostMapping()
    public ResponseEntity<Long> screenCreate(@Valid @RequestBody ScreenCreateRequest request) {
        Long screenId = screenService.createScreen(request);
        return ResponseEntity.ok(screenId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> screenDelete(@PathVariable String id){
        log.info("Delete screen with id={}", id);
        long screenId = Long.parseLong(id);
        screenService.deleteScreen(screenId);
        return ResponseEntity.noContent().build();
    }
}