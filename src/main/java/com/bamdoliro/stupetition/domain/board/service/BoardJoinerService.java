package com.bamdoliro.stupetition.domain.board.service;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.BoardJoiner;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardJoinerRepository;
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

    private final BoardJoinerRepository boardJoinerRepository;
    private final UserService userService;
    private final BoardService boardService;

    @Transactional
    public void joinBoard(JoinBoardRequestDto dto) {
        User user = userService.getCurrentUser();

        boardJoinerRepository.save(
                user.getAuthority() == Authority.ROLE_STUDENT_COUNCIL?
                        commentBoard(user, dto):agreeBoard(user, dto)
        );
    }

    private BoardJoiner commentBoard(User user, JoinBoardRequestDto dto) {
        return BoardJoiner.builder()
                .user(user)
                .board(boardService.getBoard(dto.getBoardId()))
                .comment(dto.getComment())
                .isStudentCouncil(true)
                .build();
    }

    private BoardJoiner agreeBoard(User user, JoinBoardRequestDto dto) {
        return BoardJoiner.builder()
                .user(user)
                .board(getBoardAndAddNumberOfBoardAgreer(dto.getBoardId()))
                .comment(dto.getComment())
                .isStudentCouncil(false)
                .build();
    }

    private Board getBoardAndAddNumberOfBoardAgreer(Long id) {
        Board board = boardService.getBoard(id);
        board.addAgreer();
        return board;
    }
}
