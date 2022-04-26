package com.bamdoliro.stupetition.domain.board.service;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardRepository;
import com.bamdoliro.stupetition.domain.board.domain.type.Status;
import com.bamdoliro.stupetition.domain.board.presentation.dto.request.CreateBoardRequestDto;
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

    @Transactional
    public List<BoardResponseDto> getBoards(Status status) {
        User user = userService.getCurrentUser();

        return boardRepository.findBoardsBySchoolAndStatus(user.getSchool(), status)
                .stream().map(BoardResponseDto::of).collect(Collectors.toList());
    }
}