package com.example.movieplatform.auth.controller;

import com.example.movieplatform.auth.utils.JwtUtil;
import com.example.movieplatform.user.domain.User;
import com.example.movieplatform.user.domain.request.UserLoginRequest;
import com.example.movieplatform.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @GetMapping("/loginForm")
    public String showLoginForm() {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginRequest request,
                        HttpServletResponse response) {
        User user = userService.login(request);

        String userRole = "Member";
        if (user.getIsAdmin()) {
            userRole = "Admin";
        }

        Cookie accessToken = new Cookie("ACCESSTOKEN",
                jwtUtil.generateAccessToken(user.getUserName(), userRole));
        Cookie refreshToken = new Cookie("REFRESHTOKEN",
                jwtUtil.generateRefreshToken(user.getUserName()));

        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        return "home";
    }
}
