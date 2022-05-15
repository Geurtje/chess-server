package com.example.chess.game;

import com.example.chess.game.exception.ChessException;
import com.example.chess.game.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInvalidTraceIdException(Exception e, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof ChessException) {
            status = ((ChessException) e).getHttpErrorCode();
        }

        ErrorResponse responseBody = new ErrorResponse(status, e.getMessage());
        return new ResponseEntity<>(responseBody, status);
    }
}
