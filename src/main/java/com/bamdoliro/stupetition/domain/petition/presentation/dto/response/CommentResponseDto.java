package com.bamdoliro.stupetition.domain.petition.presentation.dto.response;

import com.bamdoliro.stupetition.domain.petition.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDto {

    private String comment;
    private LocalDateTime createdAt;

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
