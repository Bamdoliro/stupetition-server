package com.bamdoliro.stupetition.domain.school.domain.repository;

import com.bamdoliro.stupetition.domain.school.domain.School;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends CrudRepository<School, Long> {

    List<School> findByNameContaining(String name);
    Optional<School> findByName(String name);
}
