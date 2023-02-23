package com.bamdoliro.stupetition.domain.petition.presentation.dto.response;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PetitionResponse {

    private Long id;
    private String title;
    private LocalDateTime createdAt;

    public static PetitionResponse of(Petition petition) {
        return PetitionResponse.builder()
                .id(petition.getId())
                .title(petition.getTitle())
                .createdAt(petition.getCreatedAt())
                .build();
    }
}
