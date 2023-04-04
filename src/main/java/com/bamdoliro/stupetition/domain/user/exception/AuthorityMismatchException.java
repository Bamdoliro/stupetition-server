package com.bamdoliro.stupetition.domain.user.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class AuthorityMismatchException extends StupetitionException {

    public final static StupetitionException EXCEPTION = new AuthorityMismatchException();

    private AuthorityMismatchException() {
        super(ErrorCode.AUTHORITY_MISMATCH);
    }
}
