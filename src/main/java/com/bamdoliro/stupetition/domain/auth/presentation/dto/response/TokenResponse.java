package com.bamdoliro.stupetition.domain.auth.presentation.dto.response;

import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
    private UserResponse user;
}
