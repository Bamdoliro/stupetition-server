package com.bamdoliro.stupetition.domain.petition.facade;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.AnswerRepository;
import com.bamdoliro.stupetition.domain.petition.exception.UserAlreadyJoinException;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerFacade {

    private final AnswerRepository answerRepository;

    public void checkCommenterPetition(User user, Petition petition) {
        if (answerRepository.existsPetitionCommenterByUserAndPetition(user, petition)) {
            throw UserAlreadyJoinException.EXCEPTION;
        }
    }
}
