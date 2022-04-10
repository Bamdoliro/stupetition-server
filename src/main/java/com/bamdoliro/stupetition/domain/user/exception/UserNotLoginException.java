package com.bamdoliro.stupetition.domain.user.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class UserNotLoginException extends StupetitionException {

    public static UserNotLoginException EXCEPTION = new UserNotLoginException();

    public UserNotLoginException() {
        super(ErrorCode.USER_NOT_LOGIN);
    }
}
