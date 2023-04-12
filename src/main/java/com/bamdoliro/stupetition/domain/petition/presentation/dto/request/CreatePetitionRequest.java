package com.bamdoliro.stupetition.domain.petition.presentation.dto.request;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePetitionRequest {

    @NotNull(message = "제목을 입력해 주세요.")
    @Size(min = 2, max = 20)
    private String title;

    @NotNull(message = "내용을 입력해 주세요.")
    @Size(min = 100, max = 4000)
    private String content;

    public Petition toEntity(User user) {
        return Petition.builder()
                .school(user.getSchool())
                .user(user)
                .title(title)
                .content(content)
                .endDate(LocalDateTime.now().plusWeeks(2))
                .build();
    }
}
