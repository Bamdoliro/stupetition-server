package com.bamdoliro.stupetition.domain.school.service;

import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.school.domain.repository.SchoolRepository;
import com.bamdoliro.stupetition.domain.school.presentation.dto.request.CreateSchoolRequest;
import com.bamdoliro.stupetition.domain.school.presentation.dto.response.SchoolResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    @Transactional(readOnly = true)
    public List<SchoolResponse> searchSchool(String schoolName) {
        List<School> result = schoolRepository.findByNameContaining(schoolName);
        return result.stream().map(SchoolResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createSchool(CreateSchoolRequest request) {
        schoolRepository.save(request.toEntity());
    }
}
