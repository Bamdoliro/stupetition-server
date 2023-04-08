package com.bamdoliro.stupetition.domain.user.domain;

import com.bamdoliro.stupetition.domain.auth.exception.PasswordMismatchException;
import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.exception.AuthorityMismatchException;
import com.bamdoliro.stupetition.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 60, nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 25, nullable = false)
    private Authority authority;

    @Column(nullable = false)
    private Integer admissionYear;

    @Builder
    public User(String name, School school, String email, String password, Authority authority, Integer admissionYear) {
        this.name = name;
        this.school = school;
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.admissionYear = admissionYear;
    }

    public void isStudentCouncil() {
        if (authority != Authority.ROLE_STUDENT_COUNCIL) {
            throw AuthorityMismatchException.EXCEPTION;
        }
    }

    public void checkPassword(String password, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(password, this.password)) {
            throw PasswordMismatchException.EXCEPTION;
        }
    }
}
