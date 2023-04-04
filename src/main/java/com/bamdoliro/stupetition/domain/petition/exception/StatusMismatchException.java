package com.bamdoliro.stupetition.domain.petition.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class StatusMismatchException extends StupetitionException {

    public final static StupetitionException EXCEPTION = new StatusMismatchException();

    private StatusMismatchException() {
        super(ErrorCode.STATUS_MISMATCH);
    }
}
