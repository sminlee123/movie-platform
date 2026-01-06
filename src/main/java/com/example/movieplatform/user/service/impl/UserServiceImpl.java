package com.example.movieplatform.user.service.impl;

import com.example.movieplatform.user.domain.User;
import com.example.movieplatform.user.domain.request.UserCreateRequest;
import com.example.movieplatform.user.domain.request.UserUpdateRequest;
import com.example.movieplatform.user.exception.UserAlreadyExistsException;
import com.example.movieplatform.user.exception.UserNotFoundException;
import com.example.movieplatform.user.repository.UserRepository;
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
        // 비밀번호 인코딩
        String password = passwordEncoder.encode(request.password());

        User user = User.of(request, password);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);

        log.info("User deleted successfully: {}", user.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public String getUserRole(String email) {
        User user = getUserByEmail(email);

        String role = "MEMBER";
        if (user.getIsAdmin()) {
            role = "ADMIN";
        }

        return role;
    }

    @Override
    public void updateUser(User user, UserUpdateRequest request) {
        user.changeUserName(request.name());
        user.changePhoneNumber(request.phoneNumber());
        user.changeBirthDay(request.birthDay());
    }
}
