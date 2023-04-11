package com.bamdoliro.stupetition.domain.petition.service;

import com.bamdoliro.stupetition.domain.petition.domain.Answer;
import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.AnswerRepository;
import com.bamdoliro.stupetition.domain.petition.domain.type.PetitionStatus;
import com.bamdoliro.stupetition.domain.petition.facade.AnswerFacade;
import com.bamdoliro.stupetition.domain.petition.facade.PetitionFacade;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.AnswerRequest;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserFacade userFacade;
    private final PetitionFacade petitionFacade;
    private final AnswerFacade answerFacade;

    @Transactional
    public void answerPetition(AnswerRequest request) {
        User user = userFacade.getCurrentUser();
        Petition petition = petitionFacade.findPetitionById(request.getPetitionId());

        petition.updateStatus(PetitionStatus.ANSWERED);
        answerRepository.save(request.toEntity(user, petition));
    }

    @Transactional
    public void deleteAnswer(Long answerId) {
        User user = userFacade.getCurrentUser();
        Answer answer = answerFacade.getAnswer(answerId);
        answerFacade.checkOwnAnswer(user, answer);

        answerRepository.delete(answer);
    }

}
