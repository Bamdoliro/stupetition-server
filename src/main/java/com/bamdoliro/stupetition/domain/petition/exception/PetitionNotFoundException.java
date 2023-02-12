package com.bamdoliro.stupetition.domain.petition.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class PetitionNotFoundException extends StupetitionException {

    public final static StupetitionException EXCEPTION = new PetitionNotFoundException();

    private PetitionNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUND);
    }
}
