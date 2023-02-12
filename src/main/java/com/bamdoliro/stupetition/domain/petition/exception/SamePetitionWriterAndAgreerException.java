package com.bamdoliro.stupetition.domain.petition.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class SamePetitionWriterAndAgreerException extends StupetitionException {

    public static final SamePetitionWriterAndAgreerException EXCEPTION = new SamePetitionWriterAndAgreerException();

     private SamePetitionWriterAndAgreerException() {
         super(ErrorCode.SAME_BOARD_WRITER_AND_AGREER);
     }
}
