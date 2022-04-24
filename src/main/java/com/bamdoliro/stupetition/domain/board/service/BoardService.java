package com.bamdoliro.stupetition.domain.board.service;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardRepository;
import com.bamdoliro.stupetition.domain.board.presentation.dto.request.CreateBoardRequestDto;
import com.bamdoliro.stupetition.domain.school.service.SchoolService;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
