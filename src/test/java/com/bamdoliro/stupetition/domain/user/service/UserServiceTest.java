package com.bamdoliro.stupetition.domain.user.service;

import com.bamdoliro.stupetition.domain.petition.domain.Approver;
import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    private final School defaultSchool = School.builder()
            .name("부산소프트웨어마이스터고등학교")
            .abbreviation("bssm")
            .build();


    @DisplayName("[Service] 학생 계정 아이디 생성")
    @Test
    void givenAgreePetitionRequestDto_whenAgreeingToPetition_thenCreatePetitionJoiner() {
        // when
        String username = String.format("%s%d%04d", defaultSchool.getAbbreviation(), 2023, 23);

        // then
        assertEquals("bssm20230023", username);
    }
}