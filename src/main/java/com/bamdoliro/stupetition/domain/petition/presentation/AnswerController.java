package com.bamdoliro.stupetition.domain.petition.presentation;

import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.AnswerRequest;
import com.bamdoliro.stupetition.domain.petition.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public void answerPetition(@RequestBody @Valid AnswerRequest request) {
        answerService.answerPetition(request);
    }

    @DeleteMapping("/{answer-id}")
    public void deleteAnswer(@PathVariable(name = "answer-id") Long answerId) {
        answerService.deleteAnswer(answerId);
    }
}
