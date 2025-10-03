package com.example.movieplatform.user.controller;

import com.example.movieplatform.auth.utils.AuthenticationUtil;
import com.example.movieplatform.user.domain.User;
import com.example.movieplatform.user.domain.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ApiUserController {

    private final AuthenticationUtil authenticationUtil;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUserInfo(){
        log.info("getCurrentUserInfo");

        User currentUser = authenticationUtil.getCurrentUser();

        UserResponse response = new UserResponse(currentUser.getId(), currentUser.getUserName());

        return ResponseEntity.ok(response);
    }

}
