package com.pet.bank.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractException extends RuntimeException{

    private final String message;
    private final HttpStatus httpStatus;

    protected AbstractException(String message, HttpStatus httpStatus){
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
