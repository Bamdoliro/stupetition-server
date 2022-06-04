package com.bamdoliro.stupetition.domain.school.presentation.dto.response;

import com.bamdoliro.stupetition.domain.school.domain.School;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SchoolResponseDto {

    private Long id;
    private String name;
    private String emailDomain;

    public static SchoolResponseDto fromEntity(School school) {
        return SchoolResponseDto.builder()
                .id(school.getId())
                .name(school.getName())
                .emailDomain(school.getEmailDomain())
                .build();
    }
}
