package com.bamdoliro.stupetition.domain.auth.presentation.dto.response;

import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;
    private UserResponseDto user;
}
