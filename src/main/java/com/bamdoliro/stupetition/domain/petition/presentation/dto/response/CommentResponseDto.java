package com.bamdoliro.stupetition.domain.petition.presentation.dto.response;

import com.bamdoliro.stupetition.domain.petition.domain.Comment;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDto {

    private Long id;
    private String comment;
    private LocalDateTime createdAt;
    private UserResponseDto writer;

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .writer(UserResponseDto.of(comment.getUser()))
                .build();
    }
}
