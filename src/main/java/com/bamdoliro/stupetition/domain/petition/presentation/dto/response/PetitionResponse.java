package com.bamdoliro.stupetition.domain.petition.presentation.dto.response;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PetitionResponse {

    private Long id;
    private String title;
    private Status status;
    private int numberOfApprover;
    private LocalDateTime createdAt;

    public static PetitionResponse of(Petition petition, int numberOfApprover) {
        return PetitionResponse.builder()
                .id(petition.getId())
                .title(petition.getTitle())
                .status(petition.getStatus())
                .numberOfApprover(numberOfApprover)
                .createdAt(petition.getCreatedAt())
                .build();
    }
}
