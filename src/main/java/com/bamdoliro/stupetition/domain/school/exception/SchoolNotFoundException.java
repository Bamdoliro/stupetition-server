package com.bamdoliro.stupetition.domain.school.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class SchoolNotFoundException extends StupetitionException {

    public final static StupetitionException EXCEPTION = new SchoolNotFoundException();

    private SchoolNotFoundException() {
        super(ErrorCode.SCHOOL_NOT_FOUND);
    }

}
