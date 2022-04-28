package com.bamdoliro.stupetition.domain.board.presentation.dto.response;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.BoardCommenter;
import com.bamdoliro.stupetition.domain.board.domain.type.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
        BoardCommenter commenter = board.getCommenter();
        String comment = null;
        if (commenter != null) {
            comment = commenter.getComment();
        }

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
                .studentCouncilComment(comment)
                .build();
    }
}
