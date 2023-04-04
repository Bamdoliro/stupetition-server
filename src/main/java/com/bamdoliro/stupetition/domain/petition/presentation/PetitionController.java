package com.bamdoliro.stupetition.domain.petition.presentation;

import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.CreatePetitionRequest;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.UpdatePetitionRequest;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.PetitionDetailResponse;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.PetitionResponse;
import com.bamdoliro.stupetition.domain.petition.service.PetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/petition")
@RequiredArgsConstructor
public class PetitionController {

    private final PetitionService petitionService;

    @PostMapping
    public void createPetition(@RequestBody @Valid CreatePetitionRequest request) {
        petitionService.createPetition(request);
    }

    @GetMapping
    public List<PetitionResponse> getPetitions(@RequestParam Status status) {
        return petitionService.getPetitions(status);
    }

    @GetMapping("/wrote")
    public List<PetitionResponse> getWrotePetitions() {
        return petitionService.getWrotePetitions();
    }

    @GetMapping("/approved")
    public List<PetitionResponse> getApprovedPetitions() {
        return petitionService.getApprovedPetitions();
    }

    @GetMapping("/{id}")
    public PetitionDetailResponse getPetitionDetail(@PathVariable Long id) {
        return petitionService.getPetitionDetail(id);
    }

    @PutMapping("/{id}")
    public void updatePetition(
            @PathVariable Long id,
            @RequestBody @Valid UpdatePetitionRequest request
    ) {
        petitionService.updatePetition(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePetition(@PathVariable Long id) {
        petitionService.deletePetition(id);
    }
}
