package com.pet.bank.exception.type;

import com.pet.bank.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class InternalServerException extends AbstractException {
    public InternalServerException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}