package com.bamdoliro.stupetition.domain.petition.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class NotOwnAnswerException extends StupetitionException {

    public final static NotOwnAnswerException EXCEPTION = new NotOwnAnswerException();

    private NotOwnAnswerException() {
        super(ErrorCode.NOT_OWN_ANSWER);
    }
}
