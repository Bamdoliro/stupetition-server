package com.bamdoliro.stupetition.domain.auth.presentation;

import com.bamdoliro.stupetition.domain.auth.service.AuthService;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.LoginUserRequestDto;
import com.bamdoliro.stupetition.domain.auth.presentation.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public TokenResponseDto loginUser(
            @RequestBody @Valid LoginUserRequestDto dto
    ) {
        return authService.loginUser(dto);
    }

    @DeleteMapping
    public void logoutUser() {
        authService.logoutUser();
    }

    @PutMapping
    public TokenResponseDto refreshToken(
            @RequestHeader(value = "Refresh-Token") String refreshToken
    ) {
        return authService.refreshToken(refreshToken);
    }
}
