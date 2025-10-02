package com.example.movieplatform.user.controller;

import com.example.movieplatform.auth.utils.AuthenticationUtil;
import com.example.movieplatform.reservation.domain.response.ReservationResponse;
import com.example.movieplatform.reservation.service.ReservationService;
import com.example.movieplatform.user.domain.User;
import com.example.movieplatform.user.domain.request.UserUpdateRequest;
import com.example.movieplatform.user.domain.response.UserDetailResponse;
import com.example.movieplatform.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final AuthenticationUtil authenticationUtil;
    private final ReservationService reservationService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Void> getMyPage() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailResponse> getMyInfo() {
        User currentUser = authenticationUtil.getCurrentUser();
        UserDetailResponse userDetailResponse = new UserDetailResponse(
                currentUser.getUserName(),
                currentUser.getEmail(),
                currentUser.getPhoneNumber(),
                currentUser.getBirthDay()
        );

        log.info(userDetailResponse.toString());
        return ResponseEntity.ok(userDetailResponse);
    }

    @GetMapping("/reservations")
    public ResponseEntity<Page<ReservationResponse>> showReservations(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        User user = authenticationUtil.getCurrentUser();
        Page<ReservationResponse> reservations = reservationService.getReservationsByUserId(user.getId(), pageable);
        return ResponseEntity.ok(reservations);
    }

    // 수정
    @PutMapping
    public ResponseEntity<Void> edit(@Valid @RequestBody UserUpdateRequest request,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

        }
        log.info(request.toString());
        User user = authenticationUtil.getCurrentUser();
        userService.updateUser(user, request);
        return ResponseEntity.ok().build();
    }

    // 탈퇴
    @DeleteMapping
    public ResponseEntity<Void> deleteMyInfo() {
        User user = authenticationUtil.getCurrentUser();
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }
}
