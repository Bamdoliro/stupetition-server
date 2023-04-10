package com.bamdoliro.stupetition.domain.petition.service;

import com.bamdoliro.stupetition.domain.petition.domain.Approver;
import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.ApproverRepository;
import com.bamdoliro.stupetition.domain.petition.domain.repository.CommentRepository;
import com.bamdoliro.stupetition.domain.petition.domain.repository.PetitionRepository;
import com.bamdoliro.stupetition.domain.petition.domain.type.PetitionStatus;
import com.bamdoliro.stupetition.domain.petition.facade.PetitionFacade;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.CreatePetitionRequest;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.UpdatePetitionRequest;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.AnswerResponse;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.PetitionDetailResponse;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.PetitionResponse;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetitionService {

    private final PetitionRepository petitionRepository;
    private final ApproverRepository approverRepository;
    private final UserFacade userFacade;
    private final UserRepository userRepository;
    private final PetitionFacade petitionFacade;
    private final CommentRepository commentRepository;

    @Transactional
    public void createPetition(CreatePetitionRequest request) {
        User user = userFacade.getCurrentUser();

        petitionRepository.save(request.toEntity(user));
    }

    @Transactional
    public List<PetitionResponse> getPetitions(PetitionStatus status) {
        User user = userFacade.getCurrentUser();

        if (status == PetitionStatus.PETITION) {
            return petitionRepository.findPetitions(user.getSchool())
                    .stream()
                    .map(this::createPetitionResponse)
                    .collect(Collectors.toList());
        }

        if (status == PetitionStatus.EXPIRED) {
            return petitionRepository.findExpiredPetitions(user.getSchool())
                    .stream()
                    .map(this::createPetitionResponse)
                    .collect(Collectors.toList());
        }

        return petitionRepository.findPetitionsBySchoolAndStatus(user.getSchool(), status)
                .stream()
                .map(this::createPetitionResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PetitionResponse> getWrotePetitions() {
        User user = userFacade.getCurrentUser();

        return petitionRepository.findPetitionsByUser(user)
                .stream().map(this::createPetitionResponse).collect(Collectors.toList());
    }

    @Transactional
    public List<PetitionResponse> getApprovedPetitions() {
        User user = userFacade.getCurrentUser();

        return approverRepository.findAllByUser(user).stream()
                .map(Approver::getPetition)
                .map(this::createPetitionResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PetitionDetailResponse getPetitionDetail(Long id) {
        User user = userFacade.getCurrentUser();
        Petition petition = petitionFacade.findPetitionById(id);

        return PetitionDetailResponse.builder()
                .id(petition.getId())
                .title(petition.getTitle())
                .content(petition.getContent())
                .status(petition.getStatus())
                .approved(petition.hasUserApproved(user, approverRepository))
                .percentageOfApprover(petition.getPercentageOfApprover(approverRepository, userRepository))
                .numberOfApprover(petition.getNumberOfApprover(approverRepository))
                .createdAt(petition.getCreatedAt())
                .comments(commentRepository.findByPetition(petition))
                .answer(petition.getStatus() == PetitionStatus.ANSWERED ?
                        petition.getAnswer().stream()
                                .map(a -> AnswerResponse.of(a, user))
                                .collect(Collectors.toList())
                        : null)
                .writer(UserResponse.of(petition.getUser()))
                .hasPermission(Objects.equals(user.getId(), petition.getUser().getId()))
                .build();
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
        Petition petition = petitionFacade.findPetitionById(id);
        petitionFacade.checkWriter(
                userFacade.getCurrentUser(),
                petition
        );

        petition.delete();
    }

    private PetitionResponse createPetitionResponse(Petition p) {
        return PetitionResponse.of(p, approverRepository, userRepository);
    }
}