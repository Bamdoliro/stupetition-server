package com.bamdoliro.stupetition.domain.board.service;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.repository.BoardRepository;
import com.bamdoliro.stupetition.domain.board.exception.BoardNotFoundException;
import com.bamdoliro.stupetition.domain.board.exception.UserAndBoardMismatchException;
import com.bamdoliro.stupetition.domain.board.presentation.dto.request.CreateBoardRequestDto;
import com.bamdoliro.stupetition.domain.board.presentation.dto.request.UpdateBoardRequestDto;
import com.bamdoliro.stupetition.domain.board.presentation.dto.response.BoardDetailResponseDto;
import com.bamdoliro.stupetition.domain.board.presentation.dto.response.BoardResponseDto;
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

import java.util.List;
import java.util.Optional;

import static com.bamdoliro.stupetition.domain.board.domain.type.Status.PETITION;
import static com.bamdoliro.stupetition.domain.user.domain.type.Status.ATTENDING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserService userService;

    private final School defaultSchool = School.builder()
            .name("부산소프트웨어마이스터고등학교")
            .emailDomain("bssm.hs.kr")
            .build();

    private final User defaultUser = User.builder()
            .school(defaultSchool)
            .email("test@test.com")
            .password("password")
            .authority(Authority.ROLE_STUDENT)
            .status(ATTENDING)
            .build();

    private final Board defaultBoard = Board.builder()
            .school(defaultSchool)
            .user(defaultUser)
            .title("title")
            .content("content")
            .build();


    @DisplayName("[Service] Board 생성")
    @Test
    void givenCreateBoardRequestDto_whenCreatingBoard_thenCreatesBoard() {
        // given
        given(userService.getCurrentUser()).willReturn(defaultUser);
        given(boardRepository.save(any())).willReturn(defaultBoard);
        ArgumentCaptor<Board> captor = ArgumentCaptor.forClass(Board.class);

        // when
        boardService.createBoard(new CreateBoardRequestDto("title", "content"));

        // then
        verify(boardRepository, times(1)).save(captor.capture());
        Board savedBoard = captor.getValue();
        assertEquals(defaultUser, savedBoard.getUser());
        assertEquals(defaultSchool, savedBoard.getSchool());
        assertEquals("title", savedBoard.getTitle());
        assertEquals("content", savedBoard.getContent());
    }

    @DisplayName("[Service] Board 조회 - Status 가 PETITION 인 경우")
    @Test
    void givenBoardStatus_whenSearchingPetitionBoardInUserSchool_thenReturnsPetitionBoardInTheSchool() {
        // given
        given(userService.getCurrentUser()).willReturn(defaultUser);
        given(boardRepository.findBoardsBySchoolAndStatus(defaultSchool, PETITION))
                .willReturn(List.of(defaultBoard, defaultBoard));

        // when
        List<BoardResponseDto> boardResponse = boardService.getBoards(PETITION);

        // then
        verify(boardRepository, times(1)).findBoardsBySchoolAndStatus(defaultUser.getSchool(), PETITION);
        assertEquals(boardResponse.get(0).getNumberOfAgreers(), defaultBoard.getNumberOfAgreers());
        assertEquals(boardResponse.get(1).getTitle(), defaultBoard.getTitle());
    }

    @DisplayName("[Service] 내가 게시한 Board 조회")
    @Test
    void givenNothing_whenSearchingUserBoard_thenReturnsUserBoard() {
        // given
        given(userService.getCurrentUser()).willReturn(defaultUser);
        given(boardRepository.findBoardsByUser(defaultUser))
                .willReturn(List.of(defaultBoard, defaultBoard));

        // when
        List<BoardResponseDto> boardResponse = boardService.getUserBoards();

        // then
        verify(boardRepository, times(1)).findBoardsByUser(defaultUser);
        assertEquals(boardResponse.get(0).getNumberOfAgreers(), defaultBoard.getNumberOfAgreers());
        assertEquals(boardResponse.get(1).getTitle(), defaultBoard.getTitle());
    }

    @DisplayName("[Service] Board 상세 조회")
    @Test
    void givenBoardId_whenSearchingBoardDetail_thenReturnsBoardDetail() {
        // given
        given(boardRepository.findBoardById(1L)).willReturn(Optional.of(defaultBoard));

        // when
        BoardDetailResponseDto boardDetailResponse = boardService.getBoardDetail(1L);

        // then
        verify(boardRepository, times(1)).findBoardById(1L);
        assertEquals(boardDetailResponse.getTitle(), defaultBoard.getTitle());
        assertEquals(boardDetailResponse.getStatus(), defaultBoard.getStatus());
    }

    @DisplayName("[Service] Board 상세 조회 - invalid BoardId")
    @Test
    void givenInvalidBoardId_whenSearchingBoardDetail_thenThrowsBoardIsNotFoundException() {
        // given
        given(boardRepository.findBoardById(1L)).willReturn(Optional.empty());

        // when and then
        assertThrows(BoardNotFoundException.class, () -> boardService.getBoardDetail(1L));
    }

    @DisplayName("[Service] Board 수정")
    @Test
    void givenBoardIdAndBoardInfo_whenModifyingBoard_thenModifiesBoard() {
        // given
        given(userService.getCurrentUser()).willReturn(defaultUser);
        given(boardRepository.findBoardById(1L)).willReturn(Optional.of(defaultBoard));

        // when
        boardService.updateBoard(1L, new UpdateBoardRequestDto("newTitle", "newContent"));

        // then
        assertEquals(defaultBoard.getTitle(), "newTitle");
        assertEquals(defaultBoard.getContent(), "newContent");
    }

    @DisplayName("[Service] Board 수정 - 글 작성자 아닌 사람이 접근")
    @Test
    void givenBoardIdAndBoardInfoByInvalidUser_whenModifyingBoard_thenThrowsUserAndBoardUserMismatchException() {
        // given
        given(userService.getCurrentUser()).willReturn(
                User.builder()
                        .email("invalid@user.com")
                        .build()
        );
        given(boardRepository.findBoardById(1L)).willReturn(Optional.of(defaultBoard));

        // when and then
        assertThrows(UserAndBoardMismatchException.class, () ->
                boardService.updateBoard(1L, new UpdateBoardRequestDto("newTitle", "newContent")));
        assertNotEquals(defaultBoard.getTitle(), "newTitle");
        assertNotEquals(defaultBoard.getContent(), "newContent");
    }

    @DisplayName("[Service] Board 삭제")
    @Test
    void givenBoardIdAndBoardInfo_whenDeletingBoard_thenDeletesBoard() {
        // given
        given(userService.getCurrentUser()).willReturn(defaultUser);
        given(boardRepository.findBoardById(1L)).willReturn(Optional.of(defaultBoard));

        // when
        boardService.deleteBoard(1L);

        // then
        verify(boardRepository, times(1)).deleteById(1L);
    }

    @DisplayName("[Service] Board 삭제 - 글 작성자 아닌 사람이 접근")
    @Test
    void givenBoardIdByInvalidUser_whenDeletingBoard_thenThrowsUserAndBoardUserMismatchException() {
        // given
        given(userService.getCurrentUser()).willReturn(
                User.builder()
                        .email("invalid@user.com")
                        .build()
        );
        given(boardRepository.findBoardById(1L)).willReturn(Optional.of(defaultBoard));

        // when and then
        assertThrows(UserAndBoardMismatchException.class, () ->
                boardService.deleteBoard (1L));
    }

}