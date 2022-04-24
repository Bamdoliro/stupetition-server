package com.bamdoliro.stupetition.domain.board.presentation;

import com.bamdoliro.stupetition.domain.board.presentation.dto.request.CreateBoardRequestDto;
import com.bamdoliro.stupetition.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public void createBoard(@RequestBody @Valid CreateBoardRequestDto dto) {
        boardService.createBoard(dto);
    }
}
