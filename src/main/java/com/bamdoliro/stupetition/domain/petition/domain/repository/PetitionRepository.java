package com.bamdoliro.stupetition.domain.petition.domain.repository;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PetitionRepository extends JpaRepository<Petition, Long> {

    List<Petition> findPetitionsBySchoolAndStatus(School school, Status status);

    List<Petition> findPetitionsByUser(User user);

    @Query("SELECT p FROM Petition p JOIN FETCH p.user WHERE p.id = :id")
    Optional<Petition> findPetitionById(Long id);

    @Query("SELECT p FROM Petition p " +
            "WHERE p.endDate < current_timestamp " +
            "ORDER BY p.endDate DESC")
    List<Petition> findExpiredPetitions(School school);

    @Query("SELECT p FROM Petition p " +
            "WHERE p.status = 'PETITION' AND p.endDate > current_timestamp ")
    List<Petition> findPetitions(School school);
}
