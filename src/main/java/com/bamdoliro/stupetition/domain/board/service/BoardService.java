package com.bamdoliro.stupetition.domain.board.service;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardRepository;
import com.bamdoliro.stupetition.domain.board.domain.type.Status;
import com.bamdoliro.stupetition.domain.board.facade.BoardFacade;
import com.bamdoliro.stupetition.domain.board.presentation.dto.request.CreateBoardRequestDto;
import com.bamdoliro.stupetition.domain.board.presentation.dto.request.UpdateBoardRequestDto;
import com.bamdoliro.stupetition.domain.board.presentation.dto.response.BoardDetailResponseDto;
import com.bamdoliro.stupetition.domain.board.presentation.dto.response.BoardResponseDto;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserFacade userFacade;
    private final BoardFacade boardFacade;

    @Transactional
    public void createBoard(CreateBoardRequestDto dto) {
        User user = userFacade.getCurrentUser();

        boardRepository.save(dto.toEntity(user));
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards(Status status) {
        User user = userFacade.getCurrentUser();


        return boardRepository.findBoardsBySchoolAndStatus(user.getSchool(), status)
                .stream().map(BoardResponseDto::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getUserBoards() {
        User user = userFacade.getCurrentUser();

        return boardRepository.findBoardsByUser(user)
                .stream().map(BoardResponseDto::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BoardDetailResponseDto getBoardDetail(Long id) {
        return BoardDetailResponseDto.of(boardFacade.findBoardById(id));
    }

    @Transactional
    public void updateBoard(Long id, UpdateBoardRequestDto dto) {
        Board board = boardFacade.findBoardById(id);
        boardFacade.checkWriter(
                userFacade.getCurrentUser(),
                board
        );

        board.updateBoard(dto.getTitle(), dto.getContent());
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardFacade.checkWriter(
                userFacade.getCurrentUser(),
                boardFacade.findBoardById(id)
        );

        boardRepository.deleteById(id);
    }

}