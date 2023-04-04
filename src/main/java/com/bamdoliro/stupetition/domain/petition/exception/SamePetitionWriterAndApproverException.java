package com.bamdoliro.stupetition.domain.petition.exception;

import com.bamdoliro.stupetition.global.error.exception.ErrorCode;
import com.bamdoliro.stupetition.global.error.exception.StupetitionException;

public class SamePetitionWriterAndApproverException extends StupetitionException {

    public static final SamePetitionWriterAndApproverException EXCEPTION = new SamePetitionWriterAndApproverException();

     private SamePetitionWriterAndApproverException() {
         super(ErrorCode.SAME_PETITION_WRITER_AND_AGREER);
     }
}
