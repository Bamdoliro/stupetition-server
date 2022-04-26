package com.bamdoliro.stupetition.domain.board.service;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardRepository;
import com.bamdoliro.stupetition.domain.board.domain.type.Status;
import com.bamdoliro.stupetition.domain.board.exception.BoardNotFoundException;
import com.bamdoliro.stupetition.domain.board.exception.UserAndBoardMismatchException;
import com.bamdoliro.stupetition.domain.board.presentation.dto.request.CreateBoardRequestDto;
import com.bamdoliro.stupetition.domain.board.presentation.dto.request.UpdateBoardRequestDto;
import com.bamdoliro.stupetition.domain.board.presentation.dto.response.BoardDetailResponseDto;
import com.bamdoliro.stupetition.domain.board.presentation.dto.response.BoardResponseDto;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

    @Transactional
    public void createBoard(CreateBoardRequestDto dto) {
        User user = userService.getCurrentUser();

        boardRepository.save(createBoardFromCreateBoardDtoAndUser(user, dto));
    }

    private Board createBoardFromCreateBoardDtoAndUser(User user, CreateBoardRequestDto dto) {
        return Board.builder()
                .school(user.getSchool())
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards(Status status) {
        User user = userService.getCurrentUser();

        return boardRepository.findBoardsBySchoolAndStatus(user.getSchool(), status)
                .stream().map(BoardResponseDto::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getUserBoards() {
        User user = userService.getCurrentUser();

        return boardRepository.findBoardsByUser(user)
                .stream().map(BoardResponseDto::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BoardDetailResponseDto getBoardDetail(Long id) {
        return BoardDetailResponseDto.of(getBoard(id));
    }

    @Transactional
    public void updateBoard(Long id, UpdateBoardRequestDto dto) {
        User user = userService.getCurrentUser();
        Board board = getBoard(id);
        validateUserAndBoard(user, board);

        board.updateBoard(dto.getTitle(), dto.getContent());
    }
    public void deleteBoard(Long id) {
        User user = userService.getCurrentUser();
        Board board = getBoard(id);
        validateUserAndBoard(user, board);

        boardRepository.deleteById(id);
    }

    private Board getBoard(Long id) {
        return boardRepository.findBoardById(id)
                .orElseThrow(() -> BoardNotFoundException.EXCEPTION);
    }

    private void validateUserAndBoard(User user, Board board) {
        if (!user.getEmail().equals(board.getUser().getEmail())) {
            throw UserAndBoardMismatchException.EXCEPTION;
        }
    }


}