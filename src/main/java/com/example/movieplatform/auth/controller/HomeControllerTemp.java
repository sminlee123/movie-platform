package com.example.movieplatform.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeControllerTemp {

    @GetMapping
    public String home() {
        return "home";
    }

}
