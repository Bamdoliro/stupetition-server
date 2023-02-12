package com.bamdoliro.stupetition.domain.petition.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class NotOwnCommentException extends StupetitionException {

    public final static NotOwnCommentException EXCEPTION = new NotOwnCommentException();

    private NotOwnCommentException() {
        super(ErrorCode.NOT_OWN_COMMENT);
    }
}
