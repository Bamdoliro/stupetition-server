package com.bamdoliro.stupetition.domain.petition.domain.repository;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.Approver;
import com.bamdoliro.stupetition.domain.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface ApproverRepository extends CrudRepository<Approver, Long> {

    Boolean existsByUserAndPetition(User user, Petition petition);
    int countByPetition(Petition petition);
}
