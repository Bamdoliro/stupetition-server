package com.bamdoliro.stupetition.domain.user.service;

import com.bamdoliro.stupetition.domain.petition.domain.repository.CommentRepository;
import com.bamdoliro.stupetition.domain.petition.domain.repository.PetitionRepository;
import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.school.facade.SchoolFacade;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.CreateStudentCouncilRequest;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.DeleteUserRequest;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.GenerateStudentsRequest;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.UpdateUserPasswordRequest;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserFacade userFacade;
    private final SchoolFacade schoolFacade;
    private final CommentRepository commentRepository;
    private final PetitionRepository petitionRepository;

    @Transactional
    public void createStudentCouncil(CreateStudentCouncilRequest request) {
        userFacade.checkUser(request.getUsername());
        userRepository.save(request.toEntity(
                passwordEncoder.encode(request.getPassword()),
                schoolFacade.findSchoolById(request.getSchoolId())));
    }


    @Transactional
    public void generateStudents(GenerateStudentsRequest request) {
        User user = userFacade.getCurrentUser();
        user.isStudentCouncil();
        School school = user.getSchool();

        userRepository.saveAll(
                IntStream.rangeClosed(1, request.getNumberOfStudents())
                        .mapToObj(index -> generateStudent(
                                index,
                                request.getAdmissionYear(),
                                request.getDefaultPassword(),
                                school
                        ))
                        .collect(Collectors.toList())
        );
    }

    private User generateStudent(Integer index, Integer admissionYear, String defaultPassword, School school) {
        String username = String.format("%s%d%04d", school.getAbbreviation(), admissionYear, index);

        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(defaultPassword))
                .authority(Authority.ROLE_STUDENT)
                .school(school)
                .build();
    }

    @Transactional(readOnly = true)
    public UserResponse getUserInformation() {
        User user = userFacade.getCurrentUser();
        return UserResponse.of(user);
    }

    @Transactional
    public void updateUserPassword(UpdateUserPasswordRequest request) {
        User user = userFacade.getCurrentUser();
        user.checkPassword(request.getCurrentPassword(), passwordEncoder);

        user.updatePassword(passwordEncoder.encode(request.getPassword()));
    }

    @Transactional
    public void deleteUser(DeleteUserRequest request) {
        User user = userFacade.getCurrentUser();
        user.checkPassword(request.getPassword(), passwordEncoder);

        commentRepository.deleteByUser(user);
        petitionRepository.deleteByUser(user);
        userRepository.delete(user);
    }
}
