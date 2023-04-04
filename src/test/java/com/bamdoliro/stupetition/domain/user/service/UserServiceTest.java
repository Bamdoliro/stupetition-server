package com.bamdoliro.stupetition.domain.user.service;

import com.bamdoliro.stupetition.domain.school.domain.School;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    private final School defaultSchool = School.builder()
            .name("부산소프트웨어마이스터고등학교")
            .domain("bssm.hs.kr")
            .build();
}