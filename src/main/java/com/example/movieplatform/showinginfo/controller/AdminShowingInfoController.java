package com.example.movieplatform.showinginfo.controller;

import com.example.movieplatform.showinginfo.domain.request.ShowingInfoCreateRequest;
import com.example.movieplatform.showinginfo.service.ShowingInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin/showings")
@RequiredArgsConstructor
public class AdminShowingInfoController {

    private final ShowingInfoService showingInfoService;

    @PostMapping
    public ResponseEntity<Void> createShowingInfo(@RequestBody ShowingInfoCreateRequest request) {
        showingInfoService.createShowingInfo(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteShowingInfo(@PathVariable Long id) {
        Long screenId = showingInfoService.deleteShowingInfo(id);
        return ResponseEntity.ok(screenId);
    }
}
