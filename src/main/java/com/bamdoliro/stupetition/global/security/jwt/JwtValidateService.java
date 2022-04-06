package com.bamdoliro.stupetition.global.security.jwt;

import com.bamdoliro.stupetition.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtValidateService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    public String getEmail(String token) {
        return jwtTokenProvider.extractAllClaims(token)
                .get("email", String.class);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = jwtTokenProvider
                .extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public boolean existsRefreshToken(String token) {
        String refreshToken = redisService.getData(getEmail(token));
        if (refreshToken != null) {
            return false;
        }
        return true;
    }

}
