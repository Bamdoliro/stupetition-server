package com.bamdoliro.stupetition.domain.auth.presentation;

import com.bamdoliro.stupetition.domain.auth.service.AuthService;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.LoginUserRequest;
import com.bamdoliro.stupetition.domain.auth.presentation.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public TokenResponse loginUser(
            @RequestBody @Valid LoginUserRequest request
    ) {
        return authService.loginUser(request);
    }

    @DeleteMapping
    public void logoutUser() {
        authService.logoutUser();
    }

    @PutMapping
    public TokenResponse refreshToken(
            @RequestHeader(value = "Refresh-Token") String refreshToken
    ) {
        return authService.refreshToken(refreshToken);
    }
}
