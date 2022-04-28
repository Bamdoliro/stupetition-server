package com.bamdoliro.stupetition.domain.board.presentation;

import com.bamdoliro.stupetition.domain.board.presentation.dto.request.JoinBoardRequestDto;
import com.bamdoliro.stupetition.domain.board.service.BoardJoinerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/board/join")
@RequiredArgsConstructor
public class BoardJoinerController {

    private final BoardJoinerService boardJoinerService;

    @PostMapping
    public void joinBoard(@RequestBody @Valid JoinBoardRequestDto dto) {
        boardJoinerService.joinBoard(dto);
    }

}
