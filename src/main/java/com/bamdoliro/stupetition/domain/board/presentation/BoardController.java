package com.bamdoliro.stupetition.domain.board.presentation;

import com.bamdoliro.stupetition.domain.board.domain.type.Status;
import com.bamdoliro.stupetition.domain.board.presentation.dto.request.CreateBoardRequestDto;
import com.bamdoliro.stupetition.domain.board.presentation.dto.request.UpdateBoardRequestDto;
import com.bamdoliro.stupetition.domain.board.presentation.dto.response.BoardDetailResponseDto;
import com.bamdoliro.stupetition.domain.board.presentation.dto.response.BoardResponseDto;
import com.bamdoliro.stupetition.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public void createBoard(@RequestBody @Valid CreateBoardRequestDto dto) {
        boardService.createBoard(dto);
    }

    @GetMapping
    public List<BoardResponseDto> getBoards(@RequestParam Status status) {
        return boardService.getBoards(status);
    }

    @GetMapping("/my")
    public List<BoardResponseDto> getUserBoards() {
        return boardService.getUserBoards();
    }

    @GetMapping("/{boardId}")
    public BoardDetailResponseDto getBoardDetail(@PathVariable Long boardId) {
        return boardService.getBoardDetail(boardId);
    }

    @PutMapping("/{boardId}")
    public void updateBoard(
            @PathVariable Long boardId,
            @RequestBody @Valid UpdateBoardRequestDto dto
    ) {
        boardService.updateBoard(boardId, dto);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
    }
}
