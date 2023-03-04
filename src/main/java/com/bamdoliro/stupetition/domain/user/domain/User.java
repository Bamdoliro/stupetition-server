package com.bamdoliro.stupetition.domain.user.domain;

import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.exception.AuthorityMismatchException;
import com.bamdoliro.stupetition.domain.user.exception.PasswordMismatchException;
import com.bamdoliro.stupetition.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(length = 20, nullable = false, unique = true)
    private String username;

    @Column(length = 60, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", length = 25, nullable = false)
    private Authority authority;

    @Builder
    public User(School school, String username, String password, Authority authority) {
        this.school = school;
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void checkPassword(String password, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(password, this.password)) {
            throw PasswordMismatchException.EXCEPTION;
        }
    }

    public void isStudentCouncil() {
        if (authority != Authority.ROLE_STUDENT_COUNCIL) {
            throw AuthorityMismatchException.EXCEPTION;
        }
    }
}
