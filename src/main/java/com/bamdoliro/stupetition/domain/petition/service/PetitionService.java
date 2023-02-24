package com.bamdoliro.stupetition.domain.petition.service;

import com.bamdoliro.stupetition.domain.petition.domain.Approver;
import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.ApproverRepository;
import com.bamdoliro.stupetition.domain.petition.domain.repository.PetitionRepository;
import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.petition.facade.PetitionFacade;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.CreatePetitionRequest;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.UpdatePetitionRequest;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.PetitionDetailResponse;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.PetitionResponse;
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
    public void createPetition(CreatePetitionRequest request) {
        User user = userFacade.getCurrentUser();

        petitionRepository.save(request.toEntity(user));
    }

    @Transactional(readOnly = true)
    public List<PetitionResponse> getPetitions(Status status) {
        User user = userFacade.getCurrentUser();

        return petitionRepository.findPetitionsBySchoolAndStatus(user.getSchool(), status)
                .stream()
                .map(this::createPetitionResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PetitionResponse> getWrotePetitions() {
        User user = userFacade.getCurrentUser();

        return petitionRepository.findPetitionsByUser(user)
                .stream().map(this::createPetitionResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PetitionResponse> getApprovedPetitions() {
        User user = userFacade.getCurrentUser();

        return approverRepository.findAllByUser(user).stream()
                .map(Approver::getPetition)
                .map(this::createPetitionResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PetitionDetailResponse getPetitionDetail(Long id) {
        User user = userFacade.getCurrentUser();
        Petition petition = petitionFacade.findPetitionById(id);

        return PetitionDetailResponse.of(
                petition,
                approverRepository.existsByUserAndPetition(user, petition),
                approverRepository.countByPetition(petition)
        );
    }

    @Transactional
    public void updatePetition(Long id, UpdatePetitionRequest request) {
        Petition petition = petitionFacade.findPetitionById(id);
        petitionFacade.checkWriter(
                userFacade.getCurrentUser(),
                petition
        );

        petition.updatePetition(request.getTitle(), request.getContent());
    }

    @Transactional
    public void deletePetition(Long id) {
        petitionFacade.checkWriter(
                userFacade.getCurrentUser(),
                petitionFacade.findPetitionById(id)
        );

        petitionRepository.deleteById(id);
    }

    private PetitionResponse createPetitionResponse(Petition p) {
        return PetitionResponse.of(p,
                approverRepository.countByPetition(p)
        );
    }
}