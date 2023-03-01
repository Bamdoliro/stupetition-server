package com.bamdoliro.stupetition.domain.petition.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class CommentNotFoundException extends StupetitionException {

    public final static StupetitionException EXCEPTION = new CommentNotFoundException();

    private CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
