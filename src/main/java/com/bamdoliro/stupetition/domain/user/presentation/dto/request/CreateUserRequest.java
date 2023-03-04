package com.bamdoliro.stupetition.domain.user.presentation.dto.request;

import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "아이디를 입력해 주세요.")
    @Length(min = 5, max = 20)
    private String username;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Length(min = 8, max = 20)
    private String password;

    @NotNull(message = "학교를 선택해 주세요.")
    private Long schoolId;

    public User toEntity(String password, School school) {
        return User.builder()
                .username(username)
                .password(password)
                .school(school)
                .authority(Authority.ROLE_STUDENT_COUNCIL)
                .build();
    }
}
