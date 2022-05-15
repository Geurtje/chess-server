package com.example.chess.game.exception;

import com.example.chess.game.model.Player;
import org.springframework.http.HttpStatus;

public class GameEndedException extends ChessException {

    private static final String EXCEPTION_MESSAGE_FORMAT = "Game '%s' has ended, '%s' is the winner";

    public GameEndedException(String gameId, Player winner) {
        super(formatExceptionMessage(gameId, winner));
    }

    private static String formatExceptionMessage(String gameId, Player winner) {
        return String.format(EXCEPTION_MESSAGE_FORMAT, gameId, winner);
    }

    @Override
    public HttpStatus getHttpErrorCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
