package com.bamdoliro.stupetition.domain.user.presentation;

import com.bamdoliro.stupetition.domain.user.presentation.dto.request.CreateUserRequest;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.DeleteUserRequest;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.UpdateUserPasswordRequest;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponse;
import com.bamdoliro.stupetition.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody @Valid CreateUserRequest request) {
        userService.createUser(request);
    }

    @GetMapping
    public UserResponse getUser() {
        return userService.getUserInformation();
    }

    @PutMapping("/update/password")
    public void updateUserPassword(@RequestBody @Valid UpdateUserPasswordRequest request) {
        userService.updateUserPassword(request);
    }

    @DeleteMapping
    private void deleteUser(@RequestBody @Valid DeleteUserRequest request) {
        userService.deleteUser(request);
    }
}
