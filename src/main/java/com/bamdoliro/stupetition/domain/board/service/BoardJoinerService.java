package com.bamdoliro.stupetition.domain.board.service;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardAgreerRepository;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardCommenterRepository;
import com.bamdoliro.stupetition.domain.board.domain.type.Status;
import com.bamdoliro.stupetition.domain.board.facade.BoardAgreerFacade;
import com.bamdoliro.stupetition.domain.board.facade.BoardCommenterFacade;
import com.bamdoliro.stupetition.domain.board.facade.BoardFacade;
import com.bamdoliro.stupetition.domain.board.presentation.dto.request.JoinBoardRequestDto;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardJoinerService {

    private final BoardAgreerRepository boardAgreerRepository;
    private final BoardCommenterRepository boardCommenterRepository;
    private final UserFacade userFacade;
    private final BoardFacade boardFacade;
    private final BoardCommenterFacade boardCommenterFacade;
    private final BoardAgreerFacade boardAgreerFacade;

    @Transactional
    public void joinBoard(JoinBoardRequestDto dto) {
        User user = userFacade.getCurrentUser();
        Board board = boardFacade.findBoardById(dto.getBoardId());

        if (user.getAuthority() == Authority.ROLE_STUDENT) {
            joinAgreerBoard(user, board, dto);
        } else {
            joinCommenterBoard(user, board, dto);
        }
    }

    private void joinCommenterBoard(User user, Board board, JoinBoardRequestDto dto) {
        boardCommenterFacade.checkCommenterBoard(user, board);

        board.updateStatus(Status.ANSWERED);
        boardCommenterRepository.save(dto.toCommenterEntity(user, board));
    }

    private void joinAgreerBoard(User user, Board board, JoinBoardRequestDto dto) {
        boardAgreerFacade.checkAgreerBoard(user, board);
        boardAgreerFacade.checkBoardWriterAndAgreer(user, board);

        board.addAgreer();
        boardAgreerRepository.save(dto.toAgreerEntity(user, board));
    }
}
