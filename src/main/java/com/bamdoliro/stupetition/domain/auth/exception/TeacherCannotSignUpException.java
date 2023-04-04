package com.bamdoliro.stupetition.domain.auth.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class TeacherCannotSignUpException extends StupetitionException {

    public final static StupetitionException EXCEPTION = new TeacherCannotSignUpException();

    private TeacherCannotSignUpException() {
        super(ErrorCode.TEACHER_CANNOT_SIGN_UP);
    }
}
