package com.bamdoliro.stupetition.domain.user.presentation.dto.response;

import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long userId;
    private String schoolName;
    private String username;
    private Authority authority;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .schoolName(user.getSchool().getName())
                .username(user.getUsername())
                .authority(user.getAuthority())
                .build();
    }
}
