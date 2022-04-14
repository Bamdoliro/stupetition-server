package com.bamdoliro.stupetition.domain.user.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UpdateUserSchoolRequestDto {

    @NotNull(message = "학교를 지정해 주세요.")
    private String schoolName;

    @NotNull(message = "현재 비밀번호를 입력해 주세요.")
    private String currentPassword;
}
