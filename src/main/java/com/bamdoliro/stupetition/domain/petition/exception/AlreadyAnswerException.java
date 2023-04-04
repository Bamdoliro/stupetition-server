package com.bamdoliro.stupetition.domain.petition.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class AlreadyAnswerException extends StupetitionException {

    public static final AlreadyAnswerException EXCEPTION = new AlreadyAnswerException();

     private AlreadyAnswerException() {
         super(ErrorCode.ALREADY_ANSWER);
     }
}
