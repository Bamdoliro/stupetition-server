package com.bamdoliro.stupetition.domain.user.service;

import com.bamdoliro.stupetition.domain.school.service.SchoolService;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.domain.type.Status;
import com.bamdoliro.stupetition.domain.user.exception.PasswordMismatchException;
import com.bamdoliro.stupetition.domain.user.exception.UserAlreadyExistsException;
import com.bamdoliro.stupetition.domain.user.exception.UserNotFoundException;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.CreateUserRequestDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.LoginUserRequestDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.GetUserResponseDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.TokenResponseDto;
import com.bamdoliro.stupetition.global.redis.RedisService;
import com.bamdoliro.stupetition.global.security.auth.AuthDetails;
import com.bamdoliro.stupetition.global.security.jwt.JwtTokenProvider;
import com.bamdoliro.stupetition.global.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static com.bamdoliro.stupetition.global.security.jwt.JwtProperties.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final CookieUtil cookieUtil;
    private final SchoolService schoolService;

    @Transactional
    public void createUser(CreateUserRequestDto dto) {
        validateCreateUserRequest(dto);
        userRepository.save(createUserFromCreateUserDto(dto));
    }

    private User createUserFromCreateUserDto(CreateUserRequestDto dto) {
        return User.builder()
                .school(schoolService.getSchool(dto.getSchoolName()))
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .authority(Authority.ROLE_STUDENT)
                .status(Status.ATTENDING)
                .build();
    }

    private void validateCreateUserRequest(CreateUserRequestDto dto) {
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> { throw UserAlreadyExistsException.EXCEPTION; });
    }

    public TokenResponseDto loginUser(LoginUserRequestDto dto, HttpServletResponse response) {
        validateLoginUserRequest(dto);

        final String accessToken = jwtTokenProvider.createAccessToken(dto.getEmail());
        final String refreshToken = jwtTokenProvider.createRefreshToken(dto.getEmail());
        redisService.setDataExpire(dto.getEmail(), refreshToken, REFRESH_TOKEN_VALID_TIME);

        Cookie accessTokenCookie = cookieUtil.createCookie(ACCESS_TOKEN_NAME, accessToken, ACCESS_TOKEN_VALID_TIME);
        Cookie refreshTokenCookie = cookieUtil.createCookie(REFRESH_TOKEN_NAME, refreshToken, REFRESH_TOKEN_VALID_TIME);
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void validateLoginUserRequest(LoginUserRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw PasswordMismatchException.EXCEPTION;
        }
    }

    @Transactional(readOnly = true)
    public GetUserResponseDto getUserInformation(Authentication authentication) {
        User user = getCurrentUser();

        return GetUserResponseDto.builder()
                .schoolName(user.getSchool().getName())
                .email(user.getEmail())
                .authority(user.getAuthority())
                .status(user.getStatus())
                .build();
    }

    public User getCurrentUser() {
        AuthDetails auth = (AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return auth.getUser();
    }
}
