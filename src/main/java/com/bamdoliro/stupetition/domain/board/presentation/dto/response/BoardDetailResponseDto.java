package com.bamdoliro.stupetition.domain.board.presentation.dto.response;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.type.Status;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class BoardDetailResponseDto {

    private Long id;
    private String userEmail;
    private String title;
    private String content;
    private Status status;
    private int numberOfAgreers;
    private List<AgreerCommentsResponseDto> agreerComments;
    private String studentCouncilComment;

    public static BoardDetailResponseDto of(Board board) {
        return BoardDetailResponseDto.builder()
                .id(board.getId())
                .userEmail(board.getUser().getEmail())
                .title(board.getTitle())
                .content(board.getContent())
                .status(board.getStatus())
                .numberOfAgreers(board.getNumberOfAgreers())
                .agreerComments(board.getAgreer().stream()
                        .map(AgreerCommentsResponseDto::of)
                        .collect(Collectors.toList()))
                .studentCouncilComment(board.getStatus() == Status.ANSWERED ? board.getCommenter().getComment() : null)
                .build();
    }
}
