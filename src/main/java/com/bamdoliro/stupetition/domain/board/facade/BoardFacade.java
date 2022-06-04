package com.bamdoliro.stupetition.domain.board.facade;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardRepository;
import com.bamdoliro.stupetition.domain.board.domain.type.Status;
import com.bamdoliro.stupetition.domain.board.exception.BoardNotFoundException;
import com.bamdoliro.stupetition.domain.board.exception.StatusMismatchException;
import com.bamdoliro.stupetition.domain.board.exception.UserIsNotWriterException;
import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardFacade {

    private final BoardRepository boardRepository;

    public Board findBoardById(Long id) {
        return boardRepository.findBoardById(id)
                .orElseThrow(() -> BoardNotFoundException.EXCEPTION);
    }

    public void checkStatue(Status expectedStatus, Status actualStatue) {
        if (expectedStatus != actualStatue) {
            throw StatusMismatchException.EXCEPTION;
        }
    }

    public void checkWriter(User user, Board board) {
        if (!user.getEmail().equals(board.getUser().getEmail())) {
            throw UserIsNotWriterException.EXCEPTION;
        }
    }
}
