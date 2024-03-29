package com.bamdoliro.stupetition.domain.school.presentation.dto.request;

import com.bamdoliro.stupetition.domain.school.domain.School;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CreateSchoolRequest {

    @NotBlank(message = "학교 이름을 입력해 주세요.")
    @Length(min = 5, max = 20)
    private String name;

    @NotBlank(message = "학교 이메일 도메인을 입력해 주세요.")
    @Length(min = 1, max = 20)
    private String domain;

    public School toEntity() {
        return School.builder()
                .name(name)
                .domain(domain)
                .build();
    }
}
