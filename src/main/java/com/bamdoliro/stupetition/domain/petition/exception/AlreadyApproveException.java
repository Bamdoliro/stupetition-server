package com.bamdoliro.stupetition.domain.petition.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class AlreadyApproveException extends StupetitionException {

    public static final AlreadyApproveException EXCEPTION = new AlreadyApproveException();

     private AlreadyApproveException() {
         super(ErrorCode.ALREADY_APPROVE);
     }
}
