package com.bamdoliro.stupetition.domain.board.presentation.dto.response;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.type.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardDetailResponseDto {

    private Long id;
    private String userEmail;
    private String title;
    private String content;
    private Status status;
    private int numberOfAgreers;

    public static BoardDetailResponseDto of(Board board) {
        return BoardDetailResponseDto.builder()
                .id(board.getId())
                .userEmail(board.getUser().getEmail())
                .title(board.getTitle())
                .content(board.getContent())
                .status(board.getStatus())
                .numberOfAgreers(board.getNumberOfAgreers())
                .build();
    }
}
