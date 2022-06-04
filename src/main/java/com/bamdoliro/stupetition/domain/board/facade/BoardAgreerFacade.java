package com.bamdoliro.stupetition.domain.board.facade;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardAgreerRepository;
import com.bamdoliro.stupetition.domain.board.exception.SameBoardWriterAndAgreerException;
import com.bamdoliro.stupetition.domain.board.exception.UserAlreadyJoinException;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardAgreerFacade {

    private final BoardAgreerRepository boardAgreerRepository;

    public void checkAgreerBoard(User user, Board board) {
        if (boardAgreerRepository.existsBoardAgreerByUserAndBoard(user, board)) {
            throw UserAlreadyJoinException.EXCEPTION;
        }
    }

    public void checkBoardWriterAndAgreer(User user, Board board) {
        if (board.getUser().equals(user)) {
            throw SameBoardWriterAndAgreerException.EXCEPTION;
        }
    }
}