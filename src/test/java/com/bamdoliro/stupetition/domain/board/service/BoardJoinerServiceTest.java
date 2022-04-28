package com.bamdoliro.stupetition.domain.board.service;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.BoardJoiner;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardJoinerRepository;
import com.bamdoliro.stupetition.domain.board.presentation.dto.request.JoinBoardRequestDto;
import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BoardJoinerServiceTest {

    @InjectMocks
    private BoardJoinerService boardJoinerService;

    @Mock
    private BoardJoinerRepository boardJoinerRepository;

    @Mock
    private UserService userService;

    @Mock
    private BoardService boardService;

    private final School defaultSchool = School.builder()
            .name("부산소프트웨어마이스터고등학교")
            .emailDomain("bssm.hs.kr")
            .build();

    private final User defaultUser = makeUser(Authority.ROLE_STUDENT);

    private final Board defaultBoard = Board.builder()
            .school(defaultSchool)
            .user(defaultUser)
            .title("title")
            .content("content")
            .build();

    private User makeUser(Authority authority) {
        return User.builder()
                .authority(authority)
                .build();
    }

    private BoardJoiner makeBoardJoiner(User user) {
        return BoardJoiner.builder()
                .user(user)
                .board(defaultBoard)
                .comment("comment")
                .isStudentCouncil(user.getAuthority() == Authority.ROLE_STUDENT_COUNCIL)
                .build();
    }

    @DisplayName("[Service] User Board 에 동의")
    @Test
    void givenAgreeBoardRequestDto_whenAgreeingToBoard_thenCreateBoardJoiner() {
        // given
        User agreer = makeUser(Authority.ROLE_STUDENT);
        given(userService.getCurrentUser()).willReturn(agreer);
        given(boardJoinerRepository.save(any()))
                .willReturn(makeBoardJoiner(agreer));
        given(boardService.getBoard(1L)).willReturn(defaultBoard);
        ArgumentCaptor<BoardJoiner> captor = ArgumentCaptor.forClass(BoardJoiner.class);

        // when
        boardJoinerService.joinBoard(new JoinBoardRequestDto(1L, "content"));

        // then
        verify(boardJoinerRepository, times(1)).save(captor.capture());
        BoardJoiner savedBoardJoiner = captor.getValue();
        assertEquals(agreer, savedBoardJoiner.getUser());
        assertEquals(defaultBoard, savedBoardJoiner.getBoard());
        assertEquals("content", savedBoardJoiner.getComment());
        assertEquals(false, savedBoardJoiner.getIsStudentCouncil());
        assertEquals(defaultBoard.getNumberOfAgreers(), 1);
    }

    @DisplayName("[Service] Board 에 comment - 학생회")
    @Test
    void givenAgreeBoardRequestDto_whenCommentingToBoard_thenCreateBoardJoiner() {
        // given
        User studentCouncil = makeUser(Authority.ROLE_STUDENT_COUNCIL);
        BoardJoiner boardJoiner = makeBoardJoiner(studentCouncil);

        given(userService.getCurrentUser()).willReturn(studentCouncil);
        given(boardJoinerRepository.save(any())).willReturn(boardJoiner);
        given(boardService.getBoard(1L)).willReturn(defaultBoard);
        ArgumentCaptor<BoardJoiner> captor = ArgumentCaptor.forClass(BoardJoiner.class);

        // when
        boardJoinerService.joinBoard(new JoinBoardRequestDto(1L, "content"));

        // then
        verify(boardJoinerRepository, times(1)).save(captor.capture());
        BoardJoiner savedBoardJoiner = captor.getValue();
        assertEquals(studentCouncil, savedBoardJoiner.getUser());
        assertEquals(defaultBoard, savedBoardJoiner.getBoard());
        assertEquals("content", savedBoardJoiner.getComment());
        assertEquals(true, savedBoardJoiner.getIsStudentCouncil());
    }

}