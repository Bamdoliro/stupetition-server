package com.bamdoliro.stupetition.domain.petition.presentation.dto.response;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PetitionResponseDto {

    private Long id;
    private String title;
    private LocalDateTime createdAt;

    public static PetitionResponseDto of(Petition petition) {
        return PetitionResponseDto.builder()
                .id(petition.getId())
                .title(petition.getTitle())
                .createdAt(petition.getCreatedAt())
                .build();
    }
}
