package com.bamdoliro.stupetition.domain.petition.facade;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.ApproverRepository;
import com.bamdoliro.stupetition.domain.petition.exception.SamePetitionWriterAndApproverException;
import com.bamdoliro.stupetition.domain.petition.exception.AlreadyApproveException;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApproverFacade {

    private final ApproverRepository approverRepository;

    public void checkApprovePetition(User user, Petition petition) {
        if (approverRepository.existsByUserAndPetition(user, petition)) {
            throw AlreadyApproveException.EXCEPTION;
        }
    }

    public void checkPetitionWriterAndApprover(User user, Petition petition) {
        if (petition.getUser().equals(user)) {
            throw SamePetitionWriterAndApproverException.EXCEPTION;
        }
    }
}