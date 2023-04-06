package com.bamdoliro.stupetition.domain.auth.presentation;

import com.bamdoliro.stupetition.domain.auth.presentation.dto.request.LoginUserRequest;
import com.bamdoliro.stupetition.domain.auth.presentation.dto.response.TokenResponse;
import com.bamdoliro.stupetition.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @GetMapping("/google")
    public String getGoogleAuthLink() {
        return authService.getGoogleAuthLink();
    }

    @PostMapping("/google/callback")
    public TokenResponse authGoogle(@RequestParam String code) {
        return authService.authGoogleWithBssm(code);
    }

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
