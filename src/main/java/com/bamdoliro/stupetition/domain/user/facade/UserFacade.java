package com.bamdoliro.stupetition.domain.user.facade;

import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import com.bamdoliro.stupetition.domain.user.exception.UserAlreadyExistsException;
import com.bamdoliro.stupetition.domain.user.exception.UserNotFoundException;
import com.bamdoliro.stupetition.global.security.auth.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        AuthDetails auth = (AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return auth.getUser();
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public void checkUser(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> { throw UserAlreadyExistsException.EXCEPTION; });
    }
}
