package com.example.movieplatform.auth.utils;

import com.example.movieplatform.auth.exception.NotAdminException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AdminUtil {

    // 컨텍스트 홀더에서 관리자 권한인지 체크하는 유틸
    // 필요한가?

    public void isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new NotAdminException();
        }
    }
}
