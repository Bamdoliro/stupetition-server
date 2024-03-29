package com.bamdoliro.stupetition.domain.petition.presentation.dto.response;

import com.bamdoliro.stupetition.domain.petition.domain.Comment;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
public class CommentResponse {

    private Long id;
    private String comment;
    private LocalDateTime createdAt;
    private UserResponse writer;
    private Boolean hasPermission;

    public static CommentResponse of(Comment comment, User user) {
        return CommentResponse.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .writer(UserResponse.of(comment.getUser()))
                .hasPermission(Objects.equals(user.getId(), comment.getUser().getId()))
                .build();
    }
}
