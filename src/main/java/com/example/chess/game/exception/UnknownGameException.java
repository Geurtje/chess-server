package com.example.chess.game.exception;

import org.springframework.http.HttpStatus;

public class UnknownGameException extends ChessException {

    private static final String EXCEPTION_MESSAGE_FORMAT = "Unknown game with id '%s'";

    public UnknownGameException(String gameId) {
        super(formatExceptionMessage(gameId));
    }

    private static String formatExceptionMessage(String gameId) {
        return String.format(EXCEPTION_MESSAGE_FORMAT, gameId);
    }

    @Override
    public HttpStatus getHttpErrorCode() {
        return HttpStatus.NOT_FOUND;
    }
}
