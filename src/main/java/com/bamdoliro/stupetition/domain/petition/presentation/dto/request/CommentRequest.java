package com.bamdoliro.stupetition.domain.petition.presentation.dto.request;

import com.bamdoliro.stupetition.domain.petition.domain.Comment;
import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    @NotNull
    private Long petitionId;

    @NotBlank
    @Size(min = 2, max = 500)
    private String comment;

    public Comment toEntity(User user, Petition petition) {
        return Comment.builder()
                .user(user)
                .petition(petition)
                .comment(comment)
                .build();
    }
}
