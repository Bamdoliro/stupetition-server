package com.bamdoliro.stupetition.domain.board.presentation.dto.response;

import com.bamdoliro.stupetition.domain.board.domain.BoardAgreer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AgreerCommentsResponseDto {

    private String comment;
    private LocalDateTime createdAt;

    public static AgreerCommentsResponseDto of(BoardAgreer boardAgreer) {
        return AgreerCommentsResponseDto.builder()
                .comment(boardAgreer.getComment())
                .createdAt(boardAgreer.getCreatedAt())
                .build();
    }
}
