package com.bamdoliro.stupetition.domain.petition.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class UserAlreadyJoinException extends StupetitionException {

    public static final UserAlreadyJoinException EXCEPTION = new UserAlreadyJoinException();

     private UserAlreadyJoinException() {
         super(ErrorCode.USER_ALREADY_JOIN);
     }
}
