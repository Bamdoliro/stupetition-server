package com.bamdoliro.stupetition.domain.board.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class BoardNotFoundException extends StupetitionException {

    public final static StupetitionException EXCEPTION = new BoardNotFoundException();

    private BoardNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUND);
    }
}
