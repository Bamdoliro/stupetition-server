package com.bamdoliro.stupetition.domain.board.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class SameBoardWriterAndAgreerException extends StupetitionException {

    public static final SameBoardWriterAndAgreerException EXCEPTION = new SameBoardWriterAndAgreerException();

     private SameBoardWriterAndAgreerException() {
         super(ErrorCode.SAME_BOARD_WRITER_AND_AGREER);
     }
}
