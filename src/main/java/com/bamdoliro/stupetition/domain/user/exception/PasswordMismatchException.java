package com.bamdoliro.stupetition.domain.user.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class PasswordMismatchException extends StupetitionException {

    public final static StupetitionException EXCEPTION = new PasswordMismatchException();

    private PasswordMismatchException() {
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}
