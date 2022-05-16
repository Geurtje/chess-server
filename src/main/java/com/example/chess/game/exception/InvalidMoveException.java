package com.example.chess.game.exception;

import org.springframework.http.HttpStatus;

public class InvalidMoveException extends ChessException {
    private static final String EXCEPTION_MESSAGE_FORMAT = "Invalid move: %s";

    public InvalidMoveException(String reason) {
        super(formatExceptionMessage(reason));
    }

    private static String formatExceptionMessage(String reason) {
        return String.format(EXCEPTION_MESSAGE_FORMAT, reason);
    }

    @Override
    public HttpStatus getHttpErrorCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
