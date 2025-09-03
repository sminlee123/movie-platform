package com.example.movieplatform.user.controller;

import com.example.movieplatform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @PostMapping("/signup")
//    public ResponseEntity<String> createUser(@RequestBody UserCreateRequest request) {
//        userService.createUser(request);
//
//        return ResponseEntity.ok("생성 성공");
//    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<String> deleteUser(@RequestBody UserDeleteRequest request) {
//        userService.deleteUser(request);
//
//        return ResponseEntity.noContent().build();
//    }
}
