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

    public School findSchoolById(Long id) {
        return schoolRepository.findById(id)
                .orElseThrow(() -> SchoolNotFoundException.EXCEPTION);
    }

    public School getSchoolByDomain(String domain) {
        return schoolRepository.findByDomain(domain)
                .orElseThrow(() -> SchoolNotFoundException.EXCEPTION);
    }
}
