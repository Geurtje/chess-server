package com.example.chess.game.http;

import com.example.chess.game.exception.ChessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInvalidTraceIdException(Exception e, WebRequest request) {
        log.error("encountered an unexpected error", e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse responseBody = new ErrorResponse(status, e.getMessage());
        return new ResponseEntity<>(responseBody, status);
    }

    @ExceptionHandler(ChessException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTraceIdException(ChessException e, WebRequest request) {
        HttpStatus status = e.getHttpErrorCode();
        ErrorResponse responseBody = new ErrorResponse(status, e.getMessage());
        return new ResponseEntity<>(responseBody, status);
    }
}
