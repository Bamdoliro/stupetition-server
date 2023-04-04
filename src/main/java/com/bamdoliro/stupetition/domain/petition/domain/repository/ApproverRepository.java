package com.bamdoliro.stupetition.domain.petition.domain.repository;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.Approver;
import com.bamdoliro.stupetition.domain.user.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApproverRepository extends CrudRepository<Approver, Long> {

    Boolean existsByUserAndPetition(User user, Petition petition);

    int countByPetition(Petition petition);

    @Query("SELECT a FROM Approver a JOIN FETCH a.petition WHERE  a.user = :user")
    List<Approver> findAllByUser(User user);
}
