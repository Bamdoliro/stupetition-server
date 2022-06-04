package com.bamdoliro.stupetition.domain.user.presentation.dto.request;

import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.domain.type.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class CreateUserRequestDto {

    @NotNull(message = "학교를 지정해 주세요.")
    private final String schoolName;

    @NotNull(message = "이메일을 입력해 주세요.")
    @Email
    private final String email;

    @NotNull(message = "비밀번호를 입력해 주세요.")
    @Size(min = 8, max = 20)
    private final String password;

    public User toEntity(School school, String password) {
        return User.builder()
                .school(school)
                .email(email)
                .password(password)
                .authority(Authority.ROLE_STUDENT)
                .status(Status.ATTENDING)
                .build();
    }
}
