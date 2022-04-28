package com.bamdoliro.stupetition.domain.board.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class JoinBoardRequestDto {

    @NotNull
    private final Long boardId;

    @NotNull
    @Size(min = 2, max = 500)
    private final String comment;
}
