package com.example.movieplatform.user.service.impl;

import com.example.movieplatform.user.domain.User;
import com.example.movieplatform.user.domain.request.UserCreateRequest;
import com.example.movieplatform.user.domain.request.UserDeleteRequest;
import com.example.movieplatform.user.exception.NotMatchPasswordException;
import com.example.movieplatform.user.exception.UserAlreadyExistsException;
import com.example.movieplatform.user.exception.UserNotFoundException;
import com.example.movieplatform.user.respository.UserRepository;
import com.example.movieplatform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserCreateRequest request) {
        if(userRepository.existsByEmail(request.email())){
            throw new UserAlreadyExistsException();
        }

        // 비밀 번호 인코딩
        String password = passwordEncoder.encode(request.password());

        User user = User.of(request, password);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(UserDeleteRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new NotMatchPasswordException();
        }

        userRepository.delete(user);

        log.info("User deleted successfully: {}",  request.email());
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public String getUserRole(String email) {
        User user = getUserByEmail(email);

        String role = "Member";
        if (user.getIsAdmin()) {
            role = "Admin";
        }

        return role;
    }
}
