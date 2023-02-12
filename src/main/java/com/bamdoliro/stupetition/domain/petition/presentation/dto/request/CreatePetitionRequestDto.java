package com.bamdoliro.stupetition.domain.petition.presentation.dto.request;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class CreatePetitionRequestDto {

    @NotNull(message = "제목을 입력해 주세요.")
    @Size(min = 2, max = 20)
    private final String title;

    @NotNull(message = "내용을 입력해 주세요.")
    @Size(min = 2, max = 4000)
    private final String content;

    public Petition toEntity(User user) {
        return Petition.builder()
                .school(user.getSchool())
                .user(user)
                .title(title)
                .content(content)
                .build();
    }
}
