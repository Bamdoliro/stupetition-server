package com.bamdoliro.stupetition.domain.user.service;

import com.bamdoliro.stupetition.domain.school.facade.SchoolFacade;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.CreateStudentCouncilRequest;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponse;
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
    public void createStudentCouncil(CreateStudentCouncilRequest request) {
        userFacade.checkUser(request.getUsername());
        userRepository.save(request.toEntity(
                passwordEncoder.encode(request.getPassword()),
                schoolFacade.findSchoolById(request.getSchoolId())));
    }

    @Transactional(readOnly = true)
    public UserResponse getUserInformation() {
        User user = userFacade.getCurrentUser();
        return UserResponse.of(user);
    }
}
