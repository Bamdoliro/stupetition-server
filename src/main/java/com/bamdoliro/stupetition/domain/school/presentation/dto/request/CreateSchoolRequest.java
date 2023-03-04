package com.bamdoliro.stupetition.domain.school.presentation.dto.request;

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
public class CreateSchoolRequest {

    @NotBlank(message = "학교 이름을 입력해 주세요.")
    @Length(min = 5, max = 20)
    private String name;

    @NotBlank(message = "약어를 입력해 주세요.")
    @Length(min = 1, max = 20)
    private String abbreviation;

    public School toEntity() {
        return School.builder()
                .name(name)
                .abbreviation(abbreviation)
                .build();
    }
}
