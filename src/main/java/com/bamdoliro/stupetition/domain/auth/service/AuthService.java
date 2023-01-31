package com.bamdoliro.stupetition.domain.auth.service;

import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.LoginUserRequestDto;
import com.bamdoliro.stupetition.domain.auth.presentation.dto.response.TokenResponseDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponseDto;
import com.bamdoliro.stupetition.global.redis.RedisService;
import com.bamdoliro.stupetition.global.security.jwt.JwtTokenProvider;
import com.bamdoliro.stupetition.global.security.jwt.JwtValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.bamdoliro.stupetition.global.security.jwt.JwtProperties.REFRESH_TOKEN_VALID_TIME;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final UserFacade userFacade;
    private final JwtValidateService jwtValidateService;

    public TokenResponseDto loginUser(LoginUserRequestDto dto) {
        User user = userFacade.findUserByEmail(dto.getEmail());
        user.checkPassword(dto.getPassword(), passwordEncoder);

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        redisService.setDataExpire(dto.getEmail(), refreshToken, REFRESH_TOKEN_VALID_TIME);

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(UserResponseDto.of(user))
                .build();
    }

    public void logoutUser() {
        User user = userFacade.getCurrentUser();
        redisService.deleteData(user.getEmail());
    }

    public TokenResponseDto refreshToken(String refreshToken) {
        return TokenResponseDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(
                        userFacade.findUserByEmail(jwtValidateService.getEmail(refreshToken))))
                .build();
    }
}
