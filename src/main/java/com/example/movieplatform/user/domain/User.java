package com.example.movieplatform.user.domain;

import com.example.movieplatform.user.domain.request.UserCreateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

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

    private User(String userName,
                 String email,
                 String password,
                 String phoneNumber,
                 LocalDate birthDay,
                 Boolean isAdmin) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.isAdmin = isAdmin;
    }

    public static User of(UserCreateRequest request, String encodePassword) {
        return new User(
                request.username(),
                request.email(),
                encodePassword,
                request.phoneNumber(),
                request.birthDay(),
                false
        );
    }

    public String getRole() {
        return isAdmin ? "ADMIN" : "MEMBER";
    }

    public void changeUserName(String userName) {
        this.userName = userName;
    }

    public void changePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void changeBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }
}
