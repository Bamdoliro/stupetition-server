package com.bamdoliro.stupetition.domain.petition.presentation.dto.request;

import com.bamdoliro.stupetition.domain.petition.domain.Answer;
import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest {

    @NotNull
    private Long petitionId;

    @NotBlank
    @Size(min = 2, max = 4000)
    private String comment;

    public Answer toEntity(User user, Petition petition) {
        return Answer.builder()
                .user(user)
                .petition(petition)
                .comment(comment)
                .build();
    }
}
