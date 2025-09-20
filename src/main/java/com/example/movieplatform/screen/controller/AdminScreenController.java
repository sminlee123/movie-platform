package com.example.movieplatform.screen.controller;

import com.example.movieplatform.screen.domain.request.ScreenCreateRequest;
import com.example.movieplatform.screen.domain.response.ScreenResponse;
import com.example.movieplatform.screen.service.ScreenService;
import com.example.movieplatform.screen.service.SeatService;
import com.example.movieplatform.showinginfo.domain.response.ShowingInfoResponse;
import com.example.movieplatform.showinginfo.service.ShowingInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/admin/screens")
@RequiredArgsConstructor
public class AdminScreenController {

    private final ScreenService screenService;
    private final SeatService seatService;
    private final ShowingInfoService showingInfoService;

    @GetMapping
    public String screenPage(@PageableDefault(size = 10, page = 0) Pageable pageable,
                             Model model) {
        Page<ScreenResponse> screenList = screenService.getAllScreens(pageable);
        model.addAttribute("screens", screenList);
        return "admin/screens";
    }

    @GetMapping("/{id}")
    public String screenDetail(@PageableDefault(size = 10, page = 0) Pageable pageable,
                               @PathVariable Long id, Model model) {
        ScreenResponse response = screenService.getScreenById(id);
        Page<ShowingInfoResponse> showingInfo = showingInfoService.getShowingInfos(pageable, id);
        model.addAttribute("screen", response);
        model.addAttribute("showingInfo", showingInfo);
        return "admin/screenDetail";
    }

    @GetMapping("/create")
    public String createForm(){
        return "admin/screenCreate";
    }

    @GetMapping("/{id}/showings/create")
    public String createShowingForm(@PathVariable Long id, Model model){
        model.addAttribute("screenId", id);
        return "/admin/showingCreate";
    }

    @PostMapping()
    public String screenCreate(@Valid @ModelAttribute ScreenCreateRequest request,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            // TODO 어떻게 할까?
            return "admin/screenCreate";
        }
        Long screenId = screenService.createScreen(request);
        seatService.generateSeats(screenId, request.rows(), request.cols());

        return "redirect:/admin/screens";
    }

    @DeleteMapping("/{id}")
    public String screenDelete(@PathVariable String id){
        log.info("Delete screen with id={}", id);
        long screenId = Long.parseLong(id);
        screenService.deleteScreen(screenId);
        return "redirect:/admin/screens";
    }
}