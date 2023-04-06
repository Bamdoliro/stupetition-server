package com.bamdoliro.stupetition.domain.petition.presentation.dto.response;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.ApproverRepository;
import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PetitionResponse {

    private Long id;
    private String title;
    private Status status;
    private int percentageOfApprover;
    private int numberOfApprover;
    private LocalDateTime createdAt;

    public static PetitionResponse of(Petition petition, ApproverRepository approverRepository, UserRepository userRepository) {
        return PetitionResponse.builder()
                .id(petition.getId())
                .title(petition.getTitle())
                .status(petition.getStatus())
                .percentageOfApprover(petition.getPercentageOfApprover(approverRepository, userRepository))
                .numberOfApprover(petition.getNumberOfApprover(approverRepository))
                .createdAt(petition.getCreatedAt())
                .build();
    }
}
