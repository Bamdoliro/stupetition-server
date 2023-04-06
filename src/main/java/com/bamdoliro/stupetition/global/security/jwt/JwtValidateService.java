package com.bamdoliro.stupetition.global.security.jwt;

import com.bamdoliro.stupetition.global.redis.RedisService;
import com.bamdoliro.stupetition.global.security.jwt.exception.ExpiredTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtValidateService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    public String getUsername(String token) {
        return jwtTokenProvider.extractAllClaims(token)
                .get("email", String.class);
    }

    public void existsRefreshToken(String token) {
        String refreshToken = redisService.getData(token);
        if (refreshToken != null) {
            throw ExpiredTokenException.EXCEPTION;
        }
    }

}
