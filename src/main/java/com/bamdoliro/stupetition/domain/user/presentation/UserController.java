package com.bamdoliro.stupetition.domain.user.presentation;

import com.bamdoliro.stupetition.domain.user.presentation.dto.request.CreateUserRequestDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.DeleteUserRequestDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.UpdateUserPasswordRequestDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponseDto;
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
    public void createUser(@RequestBody @Valid CreateUserRequestDto dto) {
        userService.createUser(dto);
    }

    @GetMapping
    public UserResponseDto getUser() {
        return userService.getUserInformation();
    }

    @PutMapping("/update/password")
    public void updateUserPassword(@RequestBody @Valid UpdateUserPasswordRequestDto dto) {
        userService.updateUserPassword(dto);
    }

    @DeleteMapping
    private void deleteUser(@RequestBody @Valid DeleteUserRequestDto dto) {
        userService.deleteUser(dto);
    }
}
