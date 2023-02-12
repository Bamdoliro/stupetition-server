package com.bamdoliro.stupetition.domain.petition.facade;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.ApproverRepository;
import com.bamdoliro.stupetition.domain.petition.exception.SamePetitionWriterAndAgreerException;
import com.bamdoliro.stupetition.domain.petition.exception.UserAlreadyJoinException;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApproverFacade {

    private final ApproverRepository approverRepository;

    public void checkAgreerPetition(User user, Petition petition) {
        if (approverRepository.existsByUserAndPetition(user, petition)) {
            throw UserAlreadyJoinException.EXCEPTION;
        }
    }

    public void checkPetitionWriterAndAgreer(User user, Petition petition) {
        if (petition.getUser().equals(user)) {
            throw SamePetitionWriterAndAgreerException.EXCEPTION;
        }
    }
}