package com.bamdoliro.stupetition.domain.board.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class UserAndBoardMismatchException extends StupetitionException {

    public final static UserAndBoardMismatchException EXCEPTION = new UserAndBoardMismatchException();

    private UserAndBoardMismatchException() {
        super(ErrorCode.USER_AND_BOARD_MISMATCH);
    }
}
