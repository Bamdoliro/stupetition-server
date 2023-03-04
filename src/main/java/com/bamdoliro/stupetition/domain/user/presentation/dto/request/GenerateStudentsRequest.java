package com.bamdoliro.stupetition.domain.user.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class GenerateStudentsRequest {

    @Max(2500)
    @Min(2000)
    @NotNull(message = "입학 연도를 입력해주세요")
    private Integer admissionYear;

    @Max(1000)
    @Min(1)
    @NotNull(message = "학생 수를 입력해주세요")
    private Integer numberOfStudents;

    @NotBlank(message = "기본 비밀번호를 입력해주세요")
    private String defaultPassword;
}
