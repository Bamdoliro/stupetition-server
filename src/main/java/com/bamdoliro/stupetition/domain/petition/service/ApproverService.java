package com.bamdoliro.stupetition.domain.petition.service;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.ApproverRepository;
import com.bamdoliro.stupetition.domain.petition.domain.repository.AnswerRepository;
import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.petition.facade.ApproverFacade;
import com.bamdoliro.stupetition.domain.petition.facade.AnswerFacade;
import com.bamdoliro.stupetition.domain.petition.facade.PetitionFacade;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.RespondPetitionRequestDto;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApproverService {

    private final ApproverRepository approverRepository;
    private final AnswerRepository answerRepository;
    private final UserFacade userFacade;
    private final PetitionFacade petitionFacade;
    private final AnswerFacade answerFacade;
    private final ApproverFacade approverFacade;

    @Transactional
    public void respondPetition(RespondPetitionRequestDto dto) {
        User user = userFacade.getCurrentUser();
        Petition petition = petitionFacade.findPetitionById(dto.getPetitionId());

        if (user.getAuthority() == Authority.ROLE_STUDENT) {
            approvePetition(user, petition, dto);
        } else {
            answerPetition(user, petition, dto);
        }
    }

    private void answerPetition(User user, Petition petition, RespondPetitionRequestDto dto) {
        answerFacade.checkCommenterPetition(user, petition);

        petition.updateStatus(Status.ANSWERED);
        answerRepository.save(dto.toAnswerEntity(user, petition));
    }

    private void approvePetition(User user, Petition petition, RespondPetitionRequestDto dto) {
        approverFacade.checkAgreerPetition(user, petition);
        approverFacade.checkPetitionWriterAndAgreer(user, petition);

        approverRepository.save(dto.toApproverEntity(user, petition));
    }
}
