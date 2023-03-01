package com.bamdoliro.stupetition.domain.petition.facade;

import com.bamdoliro.stupetition.domain.petition.domain.Answer;
import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.AnswerRepository;
import com.bamdoliro.stupetition.domain.petition.exception.AlreadyApproveException;
import com.bamdoliro.stupetition.domain.petition.exception.AnswerNotFoundException;
import com.bamdoliro.stupetition.domain.petition.exception.NotOwnCommentException;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AnswerFacade {

    private final AnswerRepository answerRepository;

    @Transactional(readOnly = true)
    public Answer getAnswer(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> AnswerNotFoundException.EXCEPTION);
    }

    public void checkAnswer(User user, Petition petition) {
        if (answerRepository.existsByUserAndPetition(user, petition)) {
            throw AlreadyApproveException.EXCEPTION;
        }
    }

    public void checkOwnAnswer(User user, Answer answer) {
        if (!answer.getUser().getId().equals(user.getId())) {
            throw NotOwnCommentException.EXCEPTION;
        }
    }
}
