package com.bamdoliro.stupetition.domain.user.service;

import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.domain.type.Status;
import com.bamdoliro.stupetition.domain.user.exception.PasswordMismatchException;
import com.bamdoliro.stupetition.domain.user.exception.UserAlreadyExistsException;
import com.bamdoliro.stupetition.domain.user.exception.UserNotFoundException;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.CreateUserRequestDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.LoginUserRequestDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.TokenResponseDto;
import com.bamdoliro.stupetition.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(CreateUserRequestDto dto) {
        validateCreateUserRequest(dto);
        userRepository.save(createUserFromCreateUserDto(dto));
    }

    private User createUserFromCreateUserDto(CreateUserRequestDto dto) {
        return User.builder()
                // TODO school mapping
                .school(null)
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

    public TokenResponseDto loginUser(LoginUserRequestDto dto) {
        validateLoginUserRequest(dto);
        String token = jwtTokenProvider.createToken(dto.getEmail());

        return TokenResponseDto.builder()
                .accessToken(token)
                .build();
    }

    private void validateLoginUserRequest(LoginUserRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw PasswordMismatchException.EXCEPTION;
        }
    }
}
