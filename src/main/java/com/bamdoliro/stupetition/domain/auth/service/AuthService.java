package com.bamdoliro.stupetition.domain.auth.service;

import com.bamdoliro.stupetition.domain.auth.exception.TeacherCannotSignUpException;
import com.bamdoliro.stupetition.domain.auth.presentation.dto.request.LoginUserRequest;
import com.bamdoliro.stupetition.domain.auth.presentation.dto.response.TokenResponse;
import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.school.facade.SchoolFacade;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.exception.AuthorityMismatchException;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import com.bamdoliro.stupetition.global.config.properties.AuthProperties;
import com.bamdoliro.stupetition.global.feign.auth.GoogleAuthClient;
import com.bamdoliro.stupetition.global.feign.auth.GoogleInformationClient;
import com.bamdoliro.stupetition.global.feign.auth.dto.request.GoogleAuthRequest;
import com.bamdoliro.stupetition.global.feign.auth.dto.response.GoogleInformationResponse;
import com.bamdoliro.stupetition.global.redis.RedisService;
import com.bamdoliro.stupetition.global.security.jwt.JwtTokenProvider;
import com.bamdoliro.stupetition.global.security.jwt.JwtValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionScoped;
import java.util.Optional;

import static com.bamdoliro.stupetition.global.security.jwt.JwtProperties.REFRESH_TOKEN_VALID_TIME;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final UserFacade userFacade;
    private final JwtValidateService jwtValidateService;
    private final GoogleAuthClient googleAuthClient;
    private final GoogleInformationClient googleInformationClient;
    private final UserRepository userRepository;
    private final AuthProperties authProperties;
    private final SchoolFacade schoolFacade;
    private final PasswordEncoder passwordEncoder;

    private static final String QUERY_STRING = "?client_id=%s&redirect_uri=%s&response_type=code&" +
            "scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";

    public String getGoogleAuthLink() {
        return authProperties.getGoogleBaseUrl() +
                String.format(QUERY_STRING, authProperties.getGoogleClientId(), authProperties.getGoogleRedirectUrl());
    }

    @Transactional
    public TokenResponse authGoogleWithBssm(String code) {
        String accessToken = googleAuthClient.getAccessToken(
                createGoogleAuthRequest(code)).getAccessToken();
        GoogleInformationResponse information = googleInformationClient.getUserInformation(accessToken);
        School school = validateEmailAndGetSchool(information.getEmail());

        Optional<User> user = userRepository.findByEmail(information.getEmail());

        if (user.isEmpty()) {
            user = Optional.of(userRepository.save(
                    User.builder()
                            .name(information.getName())
                            .school(school)
                            .email(information.getEmail())
                            .authority(Authority.ROLE_STUDENT)
                            .admissionYear(Integer.valueOf(information.getEmail().substring(0, 4)))
                            .build()
            ));
        }

        return TokenResponse.builder()
                .accessToken(jwtTokenProvider.createAccessToken(user.get()))
                .refreshToken(jwtTokenProvider.createRefreshToken(user.get()))
                .build();
    }

    private School validateEmailAndGetSchool(String email) {
        String[] splitEmail = email.split("@");

        School school = schoolFacade.getSchoolByDomain(splitEmail[1]);
        if (!splitEmail[1].equals(school.getDomain())) {
            throw AuthorityMismatchException.EXCEPTION;
        }

        if (splitEmail[0].startsWith("teacher")) {
            throw TeacherCannotSignUpException.EXCEPTION;
        }

        return school;
    }

    private GoogleAuthRequest createGoogleAuthRequest(String code) {
        return GoogleAuthRequest.builder()
                .code(code)
                .clientId(authProperties.getGoogleClientId())
                .clientSecret(authProperties.getGoogleClientSecret())
                .redirectUri(authProperties.getGoogleRedirectUrl())
                .build();
    }

    @Transactional
    public TokenResponse loginUser(LoginUserRequest request) {
        User user = userFacade.getUser(request.getUsername());
        user.checkPassword(request.getPassword(), passwordEncoder);

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        redisService.setDataExpire(refreshToken, request.getUsername(), REFRESH_TOKEN_VALID_TIME);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void logoutUser(String refreshToken) {
        redisService.deleteData(refreshToken);
    }

    @TransactionScoped
    public TokenResponse refreshToken(String refreshToken) {
        jwtValidateService.existsRefreshToken(refreshToken);
        return TokenResponse.builder()
                .accessToken(jwtTokenProvider.createAccessToken(
                        userFacade.getUser(jwtValidateService.getUsername(refreshToken))))
                .build();
    }
}
