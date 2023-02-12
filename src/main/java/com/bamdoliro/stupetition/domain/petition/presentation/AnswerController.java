package com.bamdoliro.stupetition.domain.petition.presentation;

import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.AnswerRequestDto;
import com.bamdoliro.stupetition.domain.petition.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/petition/{petition-id}/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public void answerPetition(
            @PathVariable("petition-id") Long petitionId,
            @RequestBody @Valid AnswerRequestDto dto
    ) {
        answerService.answerPetition(petitionId, dto);
    }

}
