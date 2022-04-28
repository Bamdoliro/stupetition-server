package com.bamdoliro.stupetition.domain.board.service;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.BoardAgreer;
import com.bamdoliro.stupetition.domain.board.domain.BoardCommenter;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardAgreerRepository;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardCommenterRepository;
import com.bamdoliro.stupetition.domain.board.domain.type.Status;
import com.bamdoliro.stupetition.domain.board.exception.SameBoardWriterAndAgreerException;
import com.bamdoliro.stupetition.domain.board.exception.UserAlreadyJoinException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BoardJoinerServiceTest {

    @InjectMocks
    private BoardJoinerService boardJoinerService;

    @Mock
    private BoardAgreerRepository boardAgreerRepository;

    @Mock
    private BoardCommenterRepository boardCommenterRepository;

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

    private BoardAgreer makeBoardAgreer(User user) {
        return BoardAgreer.builder()
                .user(user)
                .board(defaultBoard)
                .comment("comment")
                .build();
    }

    private BoardCommenter makeBoardCommenter(User user) {
        return BoardCommenter.builder()
                .user(user)
                .board(defaultBoard)
                .comment("comment")
                .build();
    }

    @DisplayName("[Service] Board 에 동의")
    @Test
    void givenAgreeBoardRequestDto_whenAgreeingToBoard_thenCreateBoardJoiner() {
        // given
        User student = makeUser(Authority.ROLE_STUDENT);
        BoardAgreer agreer = makeBoardAgreer(student);
        ArgumentCaptor<BoardAgreer> captor = ArgumentCaptor.forClass(BoardAgreer.class);

        given(userService.getCurrentUser()).willReturn(student);
        given(boardService.getBoard(1L)).willReturn(defaultBoard);
        given(boardAgreerRepository.existsBoardAgreerByUserAndBoard(student, defaultBoard)).willReturn(false);
        given(boardAgreerRepository.save(any())).willReturn(agreer);

        // when
        boardJoinerService.joinBoard(new JoinBoardRequestDto(1L, "content"));

        // then
        verify(boardAgreerRepository, times(1))
                .existsBoardAgreerByUserAndBoard(student, defaultBoard);
        verify(boardAgreerRepository, times(1)).save(captor.capture());
        BoardAgreer savedBoardAgreer = captor.getValue();
        assertEquals(student, savedBoardAgreer.getUser());
        assertEquals(defaultBoard, savedBoardAgreer.getBoard());
        assertEquals("content", savedBoardAgreer.getComment());
        assertEquals(defaultBoard.getNumberOfAgreers(), 1);
    }

    @DisplayName("[Service] Board 에 comment - 학생회")
    @Test
    void givenAgreeBoardRequestDto_whenCommentingToBoard_thenCreateBoardJoiner() {
        // given
        User studentCouncil = makeUser(Authority.ROLE_STUDENT_COUNCIL);
        BoardCommenter boardCommenter = makeBoardCommenter(studentCouncil);
        ArgumentCaptor<BoardCommenter> captor = ArgumentCaptor.forClass(BoardCommenter.class);

        given(userService.getCurrentUser()).willReturn(studentCouncil);
        given(boardCommenterRepository.save(any())).willReturn(boardCommenter);
        given(boardService.getBoard(1L)).willReturn(defaultBoard);
        given(boardCommenterRepository.existsBoardCommenterByUserAndBoard(studentCouncil, defaultBoard)).willReturn(false);

        // when
        boardJoinerService.joinBoard(new JoinBoardRequestDto(1L, "content"));

        // then
        verify(boardCommenterRepository, times(1)).save(captor.capture());
        BoardCommenter savedBoardCommenter = captor.getValue();
        assertEquals(studentCouncil, savedBoardCommenter.getUser());
        assertEquals(defaultBoard, savedBoardCommenter.getBoard());
        assertEquals("content", savedBoardCommenter.getComment());
        assertEquals(defaultBoard.getStatus(), Status.ANSWERED);
    }

    @DisplayName("[Service] Board 에 comment - 이미 한 경우")
    @Test
    void givenAgreeBoardRequestDto_whenCommentingToBoard_thenThrowsUserAlreadyJoinBoardException() {
        // given
        User studentCouncil = makeUser(Authority.ROLE_STUDENT_COUNCIL);

        given(userService.getCurrentUser()).willReturn(studentCouncil);
        given(boardService.getBoard(1L)).willReturn(defaultBoard);
        given(boardCommenterRepository.existsBoardCommenterByUserAndBoard(studentCouncil, defaultBoard)).willReturn(true);

        // when and then
        assertThrows(UserAlreadyJoinException.class,
                () -> boardJoinerService.joinBoard(new JoinBoardRequestDto(1L, "content")));
    }

    @DisplayName("[Service] Board 에 comment - 작성자와 동의자가 같은 경우")
    @Test
    void givenAgreeBoardRequestDto_whenAgreeingToBoard_thenThrowsSameBoardWriterAndAgreerException() {
        // given
        User student = defaultBoard.getUser();

        given(userService.getCurrentUser()).willReturn(student);
        given(boardService.getBoard(1L)).willReturn(defaultBoard);
        given(boardAgreerRepository.existsBoardAgreerByUserAndBoard(student, defaultBoard)).willReturn(false);

        // when and then
        assertThrows(SameBoardWriterAndAgreerException.class,
                () -> boardJoinerService.joinBoard(new JoinBoardRequestDto(1L, "content")));
    }

}