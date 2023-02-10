package com.bamdoliro.stupetition.domain.user.presentation.dto.request;

import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.domain.type.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class CreateUserRequestDto {

    @NotNull(message = "이메일을 입력해 주세요.")
    @Email
    private String email;

    @NotNull(message = "비밀번호를 입력해 주세요.")
    @Length(min = 8, max = 20)
    private String password;

    @NotNull(message = "학교를 선택해 주세요.")
    private Long schoolId;

    public User toEntity(String password, School school) {
        return User.builder()
                .email(email)
                .password(password)
                .school(school)
                .authority(Authority.ROLE_STUDENT)
                .status(Status.ATTENDING)
                .build();
    }
}
