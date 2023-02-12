package com.bamdoliro.stupetition.domain.petition.presentation;

import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.CreatePetitionRequestDto;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.UpdatePetitionRequestDto;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.PetitionDetailResponseDto;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.PetitionResponseDto;
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
    public void createPetition(@RequestBody @Valid CreatePetitionRequestDto dto) {
        petitionService.createPetition(dto);
    }

    @GetMapping
    public List<PetitionResponseDto> getPetitions(@RequestParam Status status) {
        return petitionService.getPetitions(status);
    }

    @GetMapping("/my")
    public List<PetitionResponseDto> getUserPetitions() {
        return petitionService.getUserPetitions();
    }

    @GetMapping("/{id}")
    public PetitionDetailResponseDto getPetitionDetail(@PathVariable Long id) {
        return petitionService.getPetitionDetail(id);
    }

    @PutMapping("/{id}")
    public void updatePetition(
            @PathVariable Long id,
            @RequestBody @Valid UpdatePetitionRequestDto dto
    ) {
        petitionService.updatePetition(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletePetition(@PathVariable Long id) {
        petitionService.deletePetition(id);
    }
}
