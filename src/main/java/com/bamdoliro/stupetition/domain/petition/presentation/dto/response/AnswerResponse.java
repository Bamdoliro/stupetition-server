package com.bamdoliro.stupetition.domain.petition.presentation.dto.response;

import com.bamdoliro.stupetition.domain.petition.domain.Answer;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
public class AnswerResponse {

    private Long id;
    private String comment;
    private LocalDateTime createdAt;
    private Boolean hasPermission;

    public static AnswerResponse of(Answer answer, User user) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .comment(answer.getComment())
                .createdAt(answer.getCreatedAt())
                .hasPermission(Objects.equals(answer.getUser().getId(), user.getId()))
                .build();
    }
}
