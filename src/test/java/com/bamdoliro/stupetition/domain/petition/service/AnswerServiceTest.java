package com.bamdoliro.stupetition.domain.petition.service;

import com.bamdoliro.stupetition.domain.petition.domain.Answer;
import com.bamdoliro.stupetition.domain.petition.domain.Approver;
import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.AnswerRepository;
import com.bamdoliro.stupetition.domain.petition.domain.repository.ApproverRepository;
import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.petition.facade.AnswerFacade;
import com.bamdoliro.stupetition.domain.petition.facade.ApproverFacade;
import com.bamdoliro.stupetition.domain.petition.facade.PetitionFacade;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.AnswerRequestDto;
import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
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
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {

    @InjectMocks
    private AnswerService answerService;

    @Mock private AnswerRepository answerRepository;
    @Mock private UserFacade userFacade;
    @Mock private PetitionFacade petitionFacade;
    @Mock private AnswerFacade answerFacade;

    private final School defaultSchool = School.builder()
            .name("부산소프트웨어마이스터고등학교")
            .emailDomain("bssm.hs.kr")
            .build();

    private final User defaultUser = createUser(Authority.ROLE_STUDENT);

    private final Petition defaultPetition = Petition.builder()
            .school(defaultSchool)
            .user(defaultUser)
            .title("title")
            .content("content")
            .build();

    private User createUser(Authority authority) {
        return User.builder()
                .authority(authority)
                .build();
    }

    private Answer createAnswer(User user) {
        return Answer.builder()
                .user(user)
                .petition(defaultPetition)
                .comment("comment")
                .build();
    }

    @DisplayName("[Service] Petition 에 comment - 학생회")
    @Test
    void givenAgreePetitionRequestDto_whenCommentingToPetition_thenCreatePetitionJoiner() {
        // given
        User studentCouncil = createUser(Authority.ROLE_STUDENT_COUNCIL);
        Answer answer = createAnswer(studentCouncil);
        ArgumentCaptor<Answer> captor = ArgumentCaptor.forClass(Answer.class);

        given(userFacade.getCurrentUser()).willReturn(studentCouncil);
        given(answerRepository.save(any())).willReturn(answer);
        given(petitionFacade.findPetitionById(1L)).willReturn(defaultPetition);
        willDoNothing().given(answerFacade).checkAnswer(studentCouncil, defaultPetition);


        // when
        answerService.answerPetition(1L, new AnswerRequestDto("content"));

        // then
        verify(answerFacade, times(1)).checkAnswer(studentCouncil, defaultPetition);
        verify(answerRepository, times(1)).save(captor.capture());
        Answer savedAnswer = captor.getValue();
        assertEquals(studentCouncil, savedAnswer.getUser());
        assertEquals(defaultPetition, savedAnswer.getPetition());
        assertEquals("content", savedAnswer.getComment());
        assertEquals(defaultPetition.getStatus(), Status.ANSWERED);
    }
}