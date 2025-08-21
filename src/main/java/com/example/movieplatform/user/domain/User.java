package com.example.movieplatform.user.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    // 로그인 용도
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    // 관리자, 일반유저 체크 용도
    @Column(nullable = false, name = "is_admin")
    private Boolean isAdmin;

    @Column(nullable = false, name = "birth")
    private LocalDate birthDay;

    @Column(nullable = false, name = "create_at")
    private LocalDateTime createAt;
}
