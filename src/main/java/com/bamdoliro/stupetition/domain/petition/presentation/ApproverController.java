package com.bamdoliro.stupetition.domain.petition.presentation;

import com.bamdoliro.stupetition.domain.petition.service.ApproverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/petition/{petition-id}/approve")
@RequiredArgsConstructor
public class ApproverController {

    private final ApproverService approverService;

    @PostMapping
    public void approvePetition(
            @PathVariable("petition-id") Long petitionId) {
        approverService.approvePetition(petitionId);
    }

}
