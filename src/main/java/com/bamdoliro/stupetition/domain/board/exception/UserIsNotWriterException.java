package com.bamdoliro.stupetition.domain.board.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class UserIsNotWriterException extends StupetitionException {

    public final static UserIsNotWriterException EXCEPTION = new UserIsNotWriterException();

    private UserIsNotWriterException() {
        super(ErrorCode.USER_IS_NOT_WRITER);
    }
}
