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

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody UserDeleteRequest request) {
        userService.deleteUser(request);

        return ResponseEntity.noContent().build();
    }
}
