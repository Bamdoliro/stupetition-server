package com.bamdoliro.stupetition.domain.board.presentation.dto.request;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class CreateBoardRequestDto {

    @NotNull(message = "제목을 입력해 주세요.")
    @Size(min = 2, max = 20)
    private final String title;

    @NotNull(message = "내용을 입력해 주세요.")
    @Size(min = 2, max = 4000)
    private final String content;

    public Board toEntity(User user) {
        return Board.builder()
                .school(user.getSchool())
                .user(user)
                .title(title)
                .content(content)
                .build();
    }
}
