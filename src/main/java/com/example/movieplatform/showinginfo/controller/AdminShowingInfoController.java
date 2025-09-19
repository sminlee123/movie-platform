package com.example.movieplatform.showinginfo.controller;

import com.example.movieplatform.showinginfo.domain.request.ShowingInfoCreateRequest;
import com.example.movieplatform.showinginfo.service.ShowingInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/admin/showings")
@RequiredArgsConstructor
public class AdminShowingInfoController {

    private final ShowingInfoService showingInfoService;

    @PostMapping
    public String createShowingInfo(@ModelAttribute ShowingInfoCreateRequest request, Model model) {
        showingInfoService.createShowingInfo(request);
        return "redirect:/admin/showings";
    }

    @DeleteMapping("/{id}")
    public String deleteShowingInfo(@PathVariable Long id) {
        Long screenId = showingInfoService.deleteShowingInfo(id);
        return "redirect:/admin/screens/" + screenId;
    }
}
