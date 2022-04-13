package com.bamdoliro.stupetition.domain.user.presentation.dto.response;

import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.domain.type.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUserResponseDto {

    private String schoolName;
    private String email;
    private Authority authority;
    private Status status;
}
