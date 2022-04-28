package com.bamdoliro.stupetition.domain.board.service;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.BoardAgreer;
import com.bamdoliro.stupetition.domain.board.domain.BoardCommenter;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardAgreerRepository;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardCommenterRepository;
import com.bamdoliro.stupetition.domain.board.domain.type.Status;
import com.bamdoliro.stupetition.domain.board.exception.SameBoardWriterAndAgreerException;
import com.bamdoliro.stupetition.domain.board.exception.UserAlreadyJoinException;
import com.bamdoliro.stupetition.domain.board.presentation.dto.request.JoinBoardRequestDto;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardJoinerService {

    private final BoardAgreerRepository boardAgreerRepository;
    private final BoardCommenterRepository boardCommenterRepository;
    private final UserService userService;
    private final BoardService boardService;

    @Transactional
    public void joinBoard(JoinBoardRequestDto dto) {
        User user = userService.getCurrentUser();
        Board board = boardService.getBoard(dto.getBoardId());

        if (user.getAuthority() == Authority.ROLE_STUDENT) {
            joinAgreerBoard(user, board, dto);
        } else {
            joinCommenterBoard(user, board, dto);
        }
    }

    private void joinCommenterBoard(User user, Board board, JoinBoardRequestDto dto) {
        validateCommenterBoard(user, board);
        board.updateStatus(Status.ANSWERED);

        boardCommenterRepository.save(createComment(user, board, dto));

    }

    private void validateCommenterBoard(User user, Board board) {
        if (boardCommenterRepository.existsBoardCommenterByUserAndBoard(user, board)) {
            throw UserAlreadyJoinException.EXCEPTION;
        }
    }

    private BoardCommenter createComment(User user, Board board, JoinBoardRequestDto dto) {
        return BoardCommenter.builder()
                .user(user)
                .board(board)
                .comment(dto.getComment())
                .build();
    }

    private void joinAgreerBoard(User user, Board board, JoinBoardRequestDto dto) {
        validateAgreerBoard(user, board);
        board.addAgreer();

        boardAgreerRepository.save(createAgree(user, board, dto));
    }

    private void validateAgreerBoard(User user, Board board) {
        if (boardAgreerRepository.existsBoardAgreerByUserAndBoard(user, board)) {
            throw UserAlreadyJoinException.EXCEPTION;
        }
        if (board.getUser().equals(user)) {
            throw SameBoardWriterAndAgreerException.EXCEPTION;
        }
    }

    private BoardAgreer createAgree(User user, Board board, JoinBoardRequestDto dto) {
        return BoardAgreer.builder()
                .user(user)
                .board(board)
                .comment(dto.getComment())
                .build();
    }

}
