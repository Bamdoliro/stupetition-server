package com.bamdoliro.stupetition.domain.petition.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePetitionRequest {

    @NotNull(message = "제목을 입력해 주세요.")
    @Size(min = 2, max = 20)
    private String title;

    @NotNull(message = "내용을 입력해 주세요.")
    @Size(min = 2, max = 4000)
    private String content;
}
