package com.bamdoliro.stupetition.domain.school.presentation;

import com.bamdoliro.stupetition.domain.school.presentation.dto.response.SchoolResponseDto;
import com.bamdoliro.stupetition.domain.school.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping("/search")
    public List<SchoolResponseDto> searchSchool(@RequestParam(name = "q") final String schoolName) {
        return schoolService.searchSchool(schoolName);
    }
}
