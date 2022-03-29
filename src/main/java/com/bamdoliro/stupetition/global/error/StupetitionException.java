package com.bamdoliro.stupetition.global.error;

import lombok.Getter;

@Getter
public class StupetitionException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public StupetitionException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public StupetitionException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
