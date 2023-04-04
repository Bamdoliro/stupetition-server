package com.bamdoliro.stupetition.domain.school.presentation.dto.response;

import com.bamdoliro.stupetition.domain.school.domain.School;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SchoolResponse {

    private Long id;
    private String name;
    private String domain;

    public static SchoolResponse fromEntity(School school) {
        return SchoolResponse.builder()
                .id(school.getId())
                .name(school.getName())
                .domain(school.getDomain())
                .build();
    }
}
