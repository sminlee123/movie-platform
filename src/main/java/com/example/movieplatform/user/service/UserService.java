package com.example.movieplatform.user.service;

import com.example.movieplatform.user.domain.User;
import com.example.movieplatform.user.domain.request.UserCreateRequest;
import com.example.movieplatform.user.domain.request.UserDeleteRequest;
import com.example.movieplatform.user.domain.request.UserLoginRequest;

public interface UserService {
    void createUser(UserCreateRequest request);
    void deleteUser(UserDeleteRequest request);
    User getUserByEmail(String email);
    String getUserRole(String email);
}
