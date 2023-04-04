package com.bamdoliro.stupetition.global.security.jwt;

import com.bamdoliro.stupetition.global.redis.RedisService;
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

    public boolean existsRefreshToken(String token) {
        String refreshToken = redisService.getData(getUsername(token));
        return refreshToken != null;
    }

}
