package com.example.chess.game.exception;

import org.springframework.http.HttpStatus;

public abstract class ChessException extends RuntimeException {

    public ChessException(String message) {
        super(message);
    }

    public ChessException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus getHttpErrorCode();

}
