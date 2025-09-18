package com.example.movieplatform.screen.controller;

import com.example.movieplatform.screen.domain.Screen;
import com.example.movieplatform.screen.domain.request.ScreenCreateRequest;
import com.example.movieplatform.screen.service.ScreenService;
import com.example.movieplatform.screen.service.SeatService;
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

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/screens")
@RequiredArgsConstructor
public class AdminScreenController {

    private final ScreenService screenService;
    private final SeatService seatService;

    // 스크린 페이지 페이징 처리 필요 ?
    // @PageableDefault(size = 10, page = 0) Pageable pageable,
    @GetMapping
    public String screenPage(Model model) {
        List<Screen> screenList = screenService.getAllScreens();
        model.addAttribute("screens", screenList);
        return "admin/screens";
    }

    @GetMapping("/{id}")
    public String screenDetail(@PathVariable Long id, Model model) {
        Screen screen = screenService.getScreenById(id);
        Long allCount = seatService.countAllSeats(id);
        Long availableCount = seatService.countAvailableSeats(id);
        model.addAttribute("screen", screen);
        model.addAttribute("allCount", allCount);
        model.addAttribute("availableCount", availableCount);
        return "admin/screenDetail";
    }

    @GetMapping("/create")
    public String createForm(){
        return "admin/screenCreate";
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