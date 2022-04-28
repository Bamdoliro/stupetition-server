package com.bamdoliro.stupetition.domain.board.domain;

import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "board_commenter_table")
@Getter
@NoArgsConstructor
public class BoardCommenter extends BoardJoiner {

    @Builder
    public BoardCommenter(User user, Board board, String comment) {
        super(user, board, comment);
    }

}
