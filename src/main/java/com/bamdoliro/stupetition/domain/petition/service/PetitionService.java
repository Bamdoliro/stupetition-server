package com.bamdoliro.stupetition.domain.petition.service;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.ApproverRepository;
import com.bamdoliro.stupetition.domain.petition.domain.repository.PetitionRepository;
import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.petition.facade.PetitionFacade;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.CreatePetitionRequestDto;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.UpdatePetitionRequestDto;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.PetitionDetailResponseDto;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.PetitionResponseDto;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetitionService {

    private final PetitionRepository petitionRepository;
    private final ApproverRepository approverRepository;
    private final UserFacade userFacade;
    private final PetitionFacade petitionFacade;

    @Transactional
    public void createPetition(CreatePetitionRequestDto dto) {
        User user = userFacade.getCurrentUser();

        petitionRepository.save(dto.toEntity(user));
    }

    @Transactional(readOnly = true)
    public List<PetitionResponseDto> getPetitions(Status status) {
        User user = userFacade.getCurrentUser();


        return petitionRepository.findPetitionsBySchoolAndStatus(user.getSchool(), status)
                .stream().map(PetitionResponseDto::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PetitionResponseDto> getUserPetitions() {
        User user = userFacade.getCurrentUser();

        return petitionRepository.findPetitionsByUser(user)
                .stream().map(PetitionResponseDto::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PetitionDetailResponseDto getPetitionDetail(Long id) {
        User user = userFacade.getCurrentUser();
        Petition petition = petitionFacade.findPetitionById(id);

        return PetitionDetailResponseDto.of(
                petition,
                approverRepository.existsByUserAndPetition(user, petition),
                approverRepository.countByPetition(petition)
        );
    }

    @Transactional
    public void updatePetition(Long id, UpdatePetitionRequestDto dto) {
        Petition petition = petitionFacade.findPetitionById(id);
        petitionFacade.checkWriter(
                userFacade.getCurrentUser(),
                petition
        );

        petition.updatePetition(dto.getTitle(), dto.getContent());
    }

    @Transactional
    public void deletePetition(Long id) {
        petitionFacade.checkWriter(
                userFacade.getCurrentUser(),
                petitionFacade.findPetitionById(id)
        );

        petitionRepository.deleteById(id);
    }

}