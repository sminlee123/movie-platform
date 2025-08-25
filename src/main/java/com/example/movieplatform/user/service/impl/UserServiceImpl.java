package com.example.movieplatform.user.service.impl;

import com.example.movieplatform.user.domain.User;
import com.example.movieplatform.user.domain.request.UserCreateRequest;
import com.example.movieplatform.user.domain.request.UserLoginRequest;
import com.example.movieplatform.user.exception.LoginFailException;
import com.example.movieplatform.user.respository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserCreateRequest request) {
        if(userRepository.existsByEmail(request.email())){
            throw new RuntimeException("Email already exists"); // 예외 수정하기
        }

        // 비밀 번호 인코딩
        String password = passwordEncoder.encode(request.password());

        User user = User.of(request, password);
        userRepository.save(user);
    }

    public User login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(LoginFailException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new LoginFailException();
        }

        return user;
    }
}
