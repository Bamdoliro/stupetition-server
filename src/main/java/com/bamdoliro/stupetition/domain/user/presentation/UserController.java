package com.bamdoliro.stupetition.domain.user.presentation;

import com.bamdoliro.stupetition.domain.user.presentation.dto.request.CreateUserRequestDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.request.LoginUserRequestDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.GetUserResponseDto;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.TokenResponseDto;
import com.bamdoliro.stupetition.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public void createUser(@RequestBody @Valid CreateUserRequestDto dto) {
        userService.createUser(dto);
    }

    @PostMapping("/login")
    public TokenResponseDto loginUser(
            @RequestBody @Valid LoginUserRequestDto dto,
            HttpServletResponse response
    ) {
        return userService.loginUser(dto, response);
    }

    @GetMapping
    public GetUserResponseDto getUser() {
        return userService.getUserInformation();
    }
}
