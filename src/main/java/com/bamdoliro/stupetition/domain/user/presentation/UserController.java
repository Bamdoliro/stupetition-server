package com.bamdoliro.stupetition.domain.user.presentation;

import com.bamdoliro.stupetition.domain.user.presentation.dto.request.CreateStudentCouncilRequest;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponse;
import com.bamdoliro.stupetition.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createStudentCouncil(
            @RequestBody @Valid CreateStudentCouncilRequest request) {
        userService.createStudentCouncil(request);
    }

    @GetMapping
    public UserResponse getUser() {
        return userService.getUserInformation();
    }
}
