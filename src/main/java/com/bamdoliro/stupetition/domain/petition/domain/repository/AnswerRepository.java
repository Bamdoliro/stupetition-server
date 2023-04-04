package com.bamdoliro.stupetition.domain.petition.domain.repository;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.Answer;
import com.bamdoliro.stupetition.domain.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    Boolean existsByUserAndPetition(User user, Petition petition);
}
