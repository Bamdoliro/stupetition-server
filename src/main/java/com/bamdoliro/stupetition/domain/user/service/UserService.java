package com.bamdoliro.stupetition.domain.user.service;

import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.school.facade.SchoolFacade;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.*;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SchoolFacade schoolFacade;
    private final UserFacade userFacade;

    @Transactional
    public void createUser(CreateUserRequestDto dto) {
        userFacade.checkUser(dto.getEmail());

        School school = schoolFacade.findSchoolById(dto.getSchoolId());
        school.addMember();

        userRepository.save(dto.toEntity(school, passwordEncoder.encode(dto.getPassword())));
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserInformation() {
        User user = userFacade.getCurrentUser();
        return UserResponseDto.of(user);
    }

    @Transactional
    public void updateUserSchool(UpdateUserSchoolRequestDto dto) {
        User user = userFacade.getCurrentUser();
        user.checkPassword(dto.getCurrentPassword(), passwordEncoder);

        user.getSchool().subtractMember();
        School updateSchool = schoolFacade.findSchoolById(dto.getSchoolId());
        updateSchool.addMember();

        user.updateSchool(updateSchool);
    }

    @Transactional
    public void updateUserPassword(UpdateUserPasswordRequestDto dto) {
        User user = userFacade.getCurrentUser();
        user.checkPassword(dto.getCurrentPassword(), passwordEncoder);

        user.updatePassword(passwordEncoder.encode(dto.getPassword()));
    }

    @Transactional
    public void deleteUser(DeleteUserRequestDto dto) {
        User user = userFacade.getCurrentUser();
        user.checkPassword(dto.getPassword(), passwordEncoder);

        user.getSchool().subtractMember();

        userRepository.delete(user);
    }
}
