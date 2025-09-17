package com.example.movieplatform.genre.controller;

import com.example.movieplatform.genre.domain.request.GenreCreateRequest;
import com.example.movieplatform.genre.domain.response.GenreResponse;
import com.example.movieplatform.genre.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/genres")
@RequiredArgsConstructor
public class AdminGenreController {

    private final GenreService genreService;

    // 장르페이지 페이징처리하기
    @GetMapping
    public String genrePage(@PageableDefault(size = 10, page = 0) Pageable pageable,
                            Model model) {
        Page<GenreResponse> genreList = genreService.allGenres(pageable);
        model.addAttribute("genres", genreList);
        return "admin/genres";
    }

    @GetMapping("/create")
    public String createForm(){
        return "admin/genreCreate";
    }

    @PostMapping()
    public String genreCreate(@ModelAttribute GenreCreateRequest request){
        genreService.createGenre(request);
        return "redirect:/admin/genres";
    }

    @DeleteMapping("/{id}")
    public String genreDelete(@PathVariable String id){
        log.info("Delete genre with id={}", id);
        long genreId = Long.parseLong(id);
        genreService.deleteGenre(genreId);
        return "redirect:/admin/genres";
    }
}
