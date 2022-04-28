package com.bamdoliro.stupetition.domain.board.domain;

import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Table;

@Table(name = "board_commenter_table")
@Getter
public class BoardCommenter extends BoardJoiner {

    @Builder
    public BoardCommenter(User user, Board board, String comment) {
        super(user, board, comment);
    }
}
