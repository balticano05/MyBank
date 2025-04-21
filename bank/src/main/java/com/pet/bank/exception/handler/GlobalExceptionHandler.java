package com.pet.bank.exception.handler;

import com.pet.bank.exception.AbstractException;
import com.pet.bank.exception.response.ErrorResponse;
import com.pet.bank.exception.type.AuthenticationFailedException;
import com.pet.bank.exception.type.CustomIOException;
import com.pet.bank.exception.type.CustomServletException;
import com.pet.bank.exception.type.MissingTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    public ResponseEntity<ErrorResponse> handleAbstractException(AbstractException ex) {

        ex.printStackTrace();


        log.error(ex.getMessage());

        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorResponse(ex.getHttpStatus(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {

        ex.printStackTrace();

        log.error(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResult(EmptyResultDataAccessException ex) {

        ex.printStackTrace();

        log.error(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND, "Resource not found"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        ex.printStackTrace();

        log.error(ex.getMessage());

        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST, errorMessage));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex) {

        ex.printStackTrace();

        log.error(ex.getMessage());

        String message = "Required parameter '" + ex.getParameterName() + "' is missing";
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, message);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthenticationFailedException.class, MissingTokenException.class})
    public ResponseEntity<ErrorResponse> handleJwtExceptions(AbstractException ex) {

        ex.printStackTrace();

        log.error(ex.getMessage());

        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorResponse(ex.getHttpStatus(), ex.getMessage()));
    }

    @ExceptionHandler(CustomIOException.class)
    public ResponseEntity<ErrorResponse> handleCustomIOException(CustomIOException ex) {

        ex.printStackTrace();

        log.error(ex.getMessage());

        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorResponse(ex.getHttpStatus(), ex.getMessage()));
    }

    @ExceptionHandler(CustomServletException.class)
    public ResponseEntity<ErrorResponse> handleCustomServletException(CustomServletException ex) {

        ex.printStackTrace();

        log.error(ex.getMessage());

        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorResponse(ex.getHttpStatus(), ex.getMessage()));
    }

}