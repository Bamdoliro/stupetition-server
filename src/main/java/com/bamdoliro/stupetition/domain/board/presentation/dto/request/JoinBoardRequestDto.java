package com.bamdoliro.stupetition.domain.board.presentation.dto.request;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.BoardAgreer;
import com.bamdoliro.stupetition.domain.board.domain.BoardCommenter;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class JoinBoardRequestDto {

    @NotNull
    private final Long boardId;

    @NotNull
    @Size(min = 2, max = 500)
    private final String comment;

    public BoardCommenter toCommenterEntity(User user, Board board) {
        return BoardCommenter.builder()
                .user(user)
                .board(board)
                .comment(comment)
                .build();
    }

    public BoardAgreer toAgreerEntity(User user, Board board) {
        return BoardAgreer.builder()
                .user(user)
                .board(board)
                .comment(comment)
                .build();
    }
}
