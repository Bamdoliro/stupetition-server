package com.bamdoliro.stupetition.domain.board.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class NotWaitingBoardException extends StupetitionException {

    public final static StupetitionException EXCEPTION = new NotWaitingBoardException();

    private NotWaitingBoardException() {
        super(ErrorCode.NOT_WAITING_BOARD);
    }
}
