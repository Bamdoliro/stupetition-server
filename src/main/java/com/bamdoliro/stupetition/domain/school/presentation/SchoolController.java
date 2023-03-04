package com.bamdoliro.stupetition.domain.school.presentation;

import com.bamdoliro.stupetition.domain.school.presentation.dto.request.CreateSchoolRequest;
import com.bamdoliro.stupetition.domain.school.presentation.dto.response.SchoolResponse;
import com.bamdoliro.stupetition.domain.school.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping("/search")
    public List<SchoolResponse> searchSchool(@RequestParam(name = "q") final String schoolName) {
        return schoolService.searchSchool(schoolName);
    }

    @PostMapping
    public void createSchool(@RequestBody @Valid CreateSchoolRequest request) {
        schoolService.createSchool(request);
    }
}
