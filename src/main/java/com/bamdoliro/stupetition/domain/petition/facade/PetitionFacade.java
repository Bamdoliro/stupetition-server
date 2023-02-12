package com.bamdoliro.stupetition.domain.petition.facade;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.PetitionRepository;
import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.petition.exception.PetitionNotFoundException;
import com.bamdoliro.stupetition.domain.petition.exception.StatusMismatchException;
import com.bamdoliro.stupetition.domain.petition.exception.UserIsNotWriterException;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetitionFacade {

    private final PetitionRepository petitionRepository;

    public Petition findPetitionById(Long id) {
        return petitionRepository.findPetitionById(id)
                .orElseThrow(() -> PetitionNotFoundException.EXCEPTION);
    }

    public void checkStatus(Status expectedStatus, Status actualStatue) {
        if (expectedStatus != actualStatue) {
            throw StatusMismatchException.EXCEPTION;
        }
    }

    public void checkWriter(User user, Petition petition) {
        if (!user.getEmail().equals(petition.getUser().getEmail())) {
            throw UserIsNotWriterException.EXCEPTION;
        }
    }
}
