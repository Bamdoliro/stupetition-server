package com.bamdoliro.stupetition.domain.user.service;

import com.bamdoliro.stupetition.domain.school.facade.SchoolFacade;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.CreateUserRequestDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.DeleteUserRequestDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.UpdateUserPasswordRequestDto;
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
    private final UserFacade userFacade;
    private final SchoolFacade schoolFacade;

    @Transactional
    public void createUser(CreateUserRequestDto dto) {
        userFacade.checkUser(dto.getEmail());
        userRepository.save(dto.toEntity(
                passwordEncoder.encode(dto.getPassword()),
                schoolFacade.findSchoolById(dto.getSchoolId())));
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserInformation() {
        User user = userFacade.getCurrentUser();
        return UserResponseDto.of(user);
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

        userRepository.delete(user);
    }
}
