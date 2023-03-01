package com.bamdoliro.stupetition.domain.petition.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class AnswerNotFoundException extends StupetitionException {

    public final static StupetitionException EXCEPTION = new AnswerNotFoundException();

    private AnswerNotFoundException() {
        super(ErrorCode.ANSWER_NOT_FOUND);
    }
}
