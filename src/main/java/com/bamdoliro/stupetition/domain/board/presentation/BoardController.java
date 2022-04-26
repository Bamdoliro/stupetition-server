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

    @GetMapping("/{id}")
    public BoardDetailResponseDto getBoardDetail(@PathVariable Long id) {
        return boardService.getBoardDetail(id);
    }

    @PutMapping("/{id}")
    public void updateBoard(
            @PathVariable Long id,
            @RequestBody @Valid UpdateBoardRequestDto dto
    ) {
        boardService.updateBoard(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
    }
}
