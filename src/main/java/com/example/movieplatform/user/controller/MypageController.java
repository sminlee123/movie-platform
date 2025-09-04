package com.example.movieplatform.user.controller;

import com.example.movieplatform.auth.utils.AuthenticationUtil;
import com.example.movieplatform.user.domain.User;
import com.example.movieplatform.user.domain.request.UserUpdateRequest;
import com.example.movieplatform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final AuthenticationUtil authenticationUtil;
    private final UserService userService;

    // 마이페이지
    @GetMapping
    public String getMyPage(Model model) {
        return "users/mypage";
    }

    // 내 정보
    @GetMapping("/me")
    public String getMyInfo(Model model) {
        User user = authenticationUtil.getCurrentUser();
        model.addAttribute("user", user);
        return "users/userinfo";
    }

    // 수정 폼
    @GetMapping("/edit")
    public String showEditForm(Model model) {
        User user = authenticationUtil.getCurrentUser();

        UserUpdateRequest request = new UserUpdateRequest(
                user.getUserName(),
                user.getPhoneNumber(),
                user.getBirthDay()
        );
        model.addAttribute("request", request);
        return "users/editForm";
    }

    // 수정
    @PostMapping("/edit")
    public String edit(@ModelAttribute UserUpdateRequest request) {
        User user = authenticationUtil.getCurrentUser();
        userService.updateUser(user, request);
        return "redirect:/mypage/me";
    }

    // 탈퇴
    @DeleteMapping
    public String deleteMyInfo() {
        User user = authenticationUtil.getCurrentUser();
        userService.deleteUser(user);
        return "redirect:/logout";
    }
}
