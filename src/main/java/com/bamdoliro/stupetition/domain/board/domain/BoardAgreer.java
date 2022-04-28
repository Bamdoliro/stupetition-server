package com.bamdoliro.stupetition.domain.board.domain;

import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Table;

@Table(name = "board_agreer_table")
@Getter
public class BoardAgreer extends BoardJoiner {

    @Builder
    public BoardAgreer(User user, Board board, String comment) {
        super(user, board, comment);
    }
}
