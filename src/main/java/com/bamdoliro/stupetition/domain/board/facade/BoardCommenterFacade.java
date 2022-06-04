package com.bamdoliro.stupetition.domain.board.facade;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardCommenterRepository;
import com.bamdoliro.stupetition.domain.board.exception.UserAlreadyJoinException;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardCommenterFacade {

    private final BoardCommenterRepository boardCommenterRepository;

    public void checkCommenterBoard(User user, Board board) {
        if (boardCommenterRepository.existsBoardCommenterByUserAndBoard(user, board)) {
            throw UserAlreadyJoinException.EXCEPTION;
        }
    }
}
