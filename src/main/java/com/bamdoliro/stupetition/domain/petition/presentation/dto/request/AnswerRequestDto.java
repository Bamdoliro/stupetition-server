package com.bamdoliro.stupetition.domain.petition.presentation.dto.request;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.Approver;
import com.bamdoliro.stupetition.domain.petition.domain.Answer;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class AnswerRequestDto {

    @NotNull
    @Size(min = 2, max = 500)
    private final String comment;

    public Answer toEntity(User user, Petition petition) {
        return Answer.builder()
                .user(user)
                .petition(petition)
                .comment(comment)
                .build();
    }
}
