package com.bamdoliro.stupetition.domain.petition.service;

import com.bamdoliro.stupetition.domain.petition.domain.Approver;
import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.constant.PetitionConstant;
import com.bamdoliro.stupetition.domain.petition.domain.repository.ApproverRepository;
import com.bamdoliro.stupetition.domain.petition.domain.type.PetitionStatus;
import com.bamdoliro.stupetition.domain.petition.facade.ApproverFacade;
import com.bamdoliro.stupetition.domain.petition.facade.PetitionFacade;
import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApproverService {

    private final ApproverRepository approverRepository;
    private final UserFacade userFacade;
    private final PetitionFacade petitionFacade;
    private final ApproverFacade approverFacade;
    private final UserRepository userRepository;

    @Transactional
    public void approvePetition(Long petitionId) {
        User user = userFacade.getCurrentUser();
        Petition petition = petitionFacade.findPetitionById(petitionId);
        approverFacade.checkApprovePetition(user, petition);
        approverFacade.checkPetitionWriterAndApprover(user, petition);

        approverRepository.save(Approver.builder()
                .user(user)
                .petition(petition)
                .build()
        );

        updateToWaitingPetition(petition, user.getSchool());
    }

    private void updateToWaitingPetition(Petition petition, School school) {
        if ((school.getNumberOfStudents(userRepository)-1) * PetitionConstant.PETITION_AGREER_PERCENTAGE
                <= petition.getNumberOfApprover(approverRepository)) {
            petition.updateStatus(PetitionStatus.WAITING);
        }
    }
}
