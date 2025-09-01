package com.example.movieplatform.auth.controller;

import com.example.movieplatform.user.domain.User;
import com.example.movieplatform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeControllerTemp {

    private final UserService userService;

    @GetMapping
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // TODO 처리 로직을 이렇게하는게 맞을까?
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName())    ) {
            String email = authentication.getName();

            User user = userService.getUserByEmail(email);
            String name = user.getUserName();

            model.addAttribute("name", name);
        }

        return "home";
    }
}
