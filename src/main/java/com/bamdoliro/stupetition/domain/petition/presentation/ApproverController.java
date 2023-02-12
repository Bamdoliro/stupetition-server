package com.bamdoliro.stupetition.domain.petition.presentation;

import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.RespondPetitionRequestDto;
import com.bamdoliro.stupetition.domain.petition.service.ApproverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/petition/join")
@RequiredArgsConstructor
public class ApproverController {

    private final ApproverService approverService;

    @PostMapping
    public void joinPetition(@RequestBody @Valid RespondPetitionRequestDto dto) {
        approverService.respondPetition(dto);
    }

}
