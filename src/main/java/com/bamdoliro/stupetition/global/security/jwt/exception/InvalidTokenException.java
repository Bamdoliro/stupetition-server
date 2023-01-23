package com.bamdoliro.stupetition.global.security.jwt.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class InvalidTokenException extends StupetitionException {

    public final static InvalidTokenException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}