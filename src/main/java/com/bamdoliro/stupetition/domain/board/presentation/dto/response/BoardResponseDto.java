package com.bamdoliro.stupetition.domain.board.presentation.dto.response;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponseDto {

    private String title;
    private int numberOfAgreers;
    private LocalDateTime createdAt;

    public static BoardResponseDto of(Board board) {
        return BoardResponseDto.builder()
                .title(board.getTitle())
                .numberOfAgreers(board.getNumberOfAgreers())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
