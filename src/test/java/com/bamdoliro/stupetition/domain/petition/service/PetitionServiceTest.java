package com.bamdoliro.stupetition.domain.petition.service;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.ApproverRepository;
import com.bamdoliro.stupetition.domain.petition.domain.repository.PetitionRepository;
import com.bamdoliro.stupetition.domain.petition.facade.PetitionFacade;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.CreatePetitionRequest;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.UpdatePetitionRequest;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.PetitionDetailResponse;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.PetitionResponse;
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

import java.util.List;

import static com.bamdoliro.stupetition.domain.petition.domain.type.Status.PETITION;
import static com.bamdoliro.stupetition.domain.user.domain.type.Status.ATTENDING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PetitionServiceTest {

    @InjectMocks
    private PetitionService petitionService;

    @Mock private PetitionRepository petitionRepository;
    @Mock private ApproverRepository approverRepository;
    @Mock private UserFacade userFacade;
    @Mock private PetitionFacade petitionFacade;

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

    private final Petition defaultPetition = Petition.builder()
            .school(defaultSchool)
            .user(defaultUser)
            .title("title")
            .content("content")
            .build();


    @DisplayName("[Service] Petition 생성")
    @Test
    void givenCreatePetitionRequestDto_whenCreatingPetition_thenCreatesPetition() {
        // given
        given(userFacade.getCurrentUser()).willReturn(defaultUser);
        given(petitionRepository.save(any())).willReturn(defaultPetition);
        ArgumentCaptor<Petition> captor = ArgumentCaptor.forClass(Petition.class);

        // when
        petitionService.createPetition(new CreatePetitionRequest("title", "content"));

        // then
        verify(petitionRepository, times(1)).save(captor.capture());
        Petition savedPetition = captor.getValue();
        assertEquals(defaultUser, savedPetition.getUser());
        assertEquals(defaultSchool, savedPetition.getSchool());
        assertEquals("title", savedPetition.getTitle());
        assertEquals("content", savedPetition.getContent());
    }

    @DisplayName("[Service] Petition 조회 - Status 가 PETITION 인 경우")
    @Test
    void givenPetitionStatus_whenSearchingPetitionPetitionInUserSchool_thenReturnsPetitionPetitionInTheSchool() {
        // given
        given(userFacade.getCurrentUser()).willReturn(defaultUser);
        given(petitionRepository.findPetitionsBySchoolAndStatus(defaultSchool, PETITION))
                .willReturn(List.of(defaultPetition, defaultPetition));

        // when
        List<PetitionResponse> petitionResponse = petitionService.getPetitions(PETITION);

        // then
        verify(petitionRepository, times(1)).findPetitionsBySchoolAndStatus(defaultUser.getSchool(), PETITION);
        assertEquals(petitionResponse.get(1).getTitle(), defaultPetition.getTitle());
    }

    @DisplayName("[Service] 내가 게시한 Petition 조회")
    @Test
    void givenNothing_whenSearchingUserPetition_thenReturnsUserPetition() {
        // given
        given(userFacade.getCurrentUser()).willReturn(defaultUser);
        given(petitionRepository.findPetitionsByUser(defaultUser))
                .willReturn(List.of(defaultPetition, defaultPetition));

        // when
        List<PetitionResponse> petitionResponse = petitionService.getUserPetitions();

        // then
        verify(petitionRepository, times(1)).findPetitionsByUser(defaultUser);
        assertEquals(petitionResponse.get(1).getTitle(), defaultPetition.getTitle());
    }

    @DisplayName("[Service] Petition 상세 조회")
    @Test
    void givenPetitionId_whenSearchingPetitionDetail_thenReturnsPetitionDetail() {
        // given
        given(userFacade.getCurrentUser()).willReturn(defaultUser);
        given(petitionFacade.findPetitionById(1L)).willReturn(defaultPetition);
        given(approverRepository.existsByUserAndPetition(defaultUser, defaultPetition)).willReturn(false);
        given(approverRepository.countByPetition(defaultPetition)).willReturn(1);

        // when
        PetitionDetailResponse response = petitionService.getPetitionDetail(1L);

        // then
        verify(petitionFacade, times(1)).findPetitionById(1L);
        assertEquals(response.getTitle(), defaultPetition.getTitle());
        assertEquals(response.getContent(), defaultPetition.getContent());
        assertEquals(response.getStatus(), defaultPetition.getStatus());
        assertEquals(response.getApproved(), false);
        assertEquals(response.getNumberOfApprover(), 1);
    }

    @DisplayName("[Service] Petition 수정")
    @Test
    void givenPetitionIdAndPetitionInfo_whenModifyingPetition_thenModifiesPetition() {
        // given
        given(userFacade.getCurrentUser()).willReturn(defaultUser);
        given(petitionFacade.findPetitionById(1L)).willReturn(defaultPetition);
        willDoNothing().given(petitionFacade).checkWriter(defaultUser, defaultPetition);

        // when
        petitionService.updatePetition(1L, new UpdatePetitionRequest("newTitle", "newContent"));

        // then
        verify(petitionFacade, times(1)).checkWriter(defaultUser, defaultPetition);
        assertEquals(defaultPetition.getTitle(), "newTitle");
        assertEquals(defaultPetition.getContent(), "newContent");
    }

    @DisplayName("[Service] Petition 삭제")
    @Test
    void givenPetitionIdAndPetitionInfo_whenDeletingPetition_thenDeletesPetition() {
        // given
        given(userFacade.getCurrentUser()).willReturn(defaultUser);
        given(petitionFacade.findPetitionById(1L)).willReturn(defaultPetition);
        willDoNothing().given(petitionFacade).checkWriter(defaultUser, defaultPetition);

        // when
        petitionService.deletePetition(1L);

        // then
        verify(petitionFacade, times(1)).checkWriter(defaultUser, defaultPetition);
        verify(petitionRepository, times(1)).deleteById(1L);
    }
}