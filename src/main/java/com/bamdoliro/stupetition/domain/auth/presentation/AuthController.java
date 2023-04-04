package com.bamdoliro.stupetition.domain.auth.presentation;

import com.bamdoliro.stupetition.domain.auth.presentation.dto.response.TokenResponse;
import com.bamdoliro.stupetition.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
