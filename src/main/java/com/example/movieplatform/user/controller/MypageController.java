package com.example.movieplatform.user.controller;

import com.example.movieplatform.auth.utils.AuthenticationUtil;
import com.example.movieplatform.user.domain.User;
import com.example.movieplatform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @DeleteMapping
    public String deleteMyInfo() {
        User user = authenticationUtil.getCurrentUser();
        userService.deleteUser(user);
        return "redirect:/logout";
    }

}
