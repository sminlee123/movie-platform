package com.example.movieplatform.user.controller;

import com.example.movieplatform.auth.utils.JwtUtil;
import com.example.movieplatform.user.domain.User;
import com.example.movieplatform.user.domain.request.UserCreateRequest;
import com.example.movieplatform.user.domain.request.UserDeleteRequest;
import com.example.movieplatform.user.domain.request.UserLoginRequest;
import com.example.movieplatform.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody UserCreateRequest request) {
        userService.createUser(request);

        return ResponseEntity.ok("생성 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request,
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

        return ResponseEntity.ok("로그인 성공\n엑세스 토큰:" + accessToken.getValue() + "\n리프레시 토큰:"
                                + refreshToken.getValue());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody UserDeleteRequest request) {
        userService.deleteUser(request);

        return ResponseEntity.noContent().build();
    }
}
