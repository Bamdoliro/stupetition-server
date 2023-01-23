package com.bamdoliro.stupetition.domain.user.domain;

import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.domain.type.Status;
import com.bamdoliro.stupetition.domain.user.exception.PasswordMismatchException;
import com.bamdoliro.stupetition.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_table")
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

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 60, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", length = 25, nullable = false)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Status status;

    @Builder
    public User(School school, String email, String password, Authority authority, Status status) {
        this.school = school;
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.status = status;
    }

    public void updateSchool(School school) {
        this.school = school;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw PasswordMismatchException.EXCEPTION;
        }
    }
}
