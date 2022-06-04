package com.bamdoliro.stupetition.domain.school.facade;

import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.school.domain.repository.SchoolRepository;
import com.bamdoliro.stupetition.domain.school.exception.SchoolNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchoolFacade {

    private final SchoolRepository schoolRepository;

    public School findSchoolByName(String name) {
        return schoolRepository.findByName(name)
                .orElseThrow(() -> SchoolNotFoundException.EXCEPTION);
    }
}
