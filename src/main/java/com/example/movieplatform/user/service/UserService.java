package com.example.movieplatform.user.service;

import com.example.movieplatform.user.domain.User;
import com.example.movieplatform.user.domain.request.UserCreateRequest;
import com.example.movieplatform.user.domain.request.UserUpdateRequest;

public interface UserService {
    void createUser(UserCreateRequest request);
    void deleteUser(User user);
    User getUserByEmail(String email);
    String getUserRole(String email);
    void updateUser(User user, UserUpdateRequest request);
}
