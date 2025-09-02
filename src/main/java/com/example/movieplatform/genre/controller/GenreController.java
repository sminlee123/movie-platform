package com.example.movieplatform.genre.controller;

import com.example.movieplatform.auth.utils.AdminUtil;
import com.example.movieplatform.genre.domain.request.GenreCreateRequest;
import com.example.movieplatform.genre.domain.response.GenreResponse;
import com.example.movieplatform.genre.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;
    private final AdminUtil adminUtil;

    //TODO 관리자 컨트롤러로 옮기기

    @GetMapping
    public String genrePage(Model model) {
        List<GenreResponse> genreList = genreService.listGenres();

        model.addAttribute("genres", genreList);
        return "/genres/list";
    }

    @GetMapping("/create")
    public String createForm(){
        adminUtil.isAdmin();

        return "/genres/create";
    }

    @PostMapping()
    public String genreCreate(@ModelAttribute GenreCreateRequest request){
        adminUtil.isAdmin();

        genreService.createGenre(request);

        return "redirect:/genres";
    }

    @DeleteMapping("/{id}")
    public String genreDelete(@PathVariable String id){
        log.info("Delete genre with id={}", id);
        adminUtil.isAdmin();

        long genreId = Long.parseLong(id);
        genreService.deleteGenre(genreId);

        return "redirect:/genres";
    }
}
