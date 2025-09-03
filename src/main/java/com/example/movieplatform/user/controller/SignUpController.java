package com.example.movieplatform.user.controller;

import com.example.movieplatform.user.domain.request.UserCreateRequest;
import com.example.movieplatform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final UserService userService;

    @GetMapping
    public String showSignUpForm(Model model) {
        // 생성 dto 바인딩
        model.addAttribute("userCreateRequest", new UserCreateRequest(
                null, null, null, null, null));
        return "users/signupForm";
    }

    @PostMapping
    public String registerUser(@ModelAttribute  UserCreateRequest request) {
        // TODO 비밀번호 2번 받아서 검증하는건 어떻게 할까
        userService.createUser(request);
        log.info("User created successfully {}", request.username());
        return "redirect:/login";
    }
}
