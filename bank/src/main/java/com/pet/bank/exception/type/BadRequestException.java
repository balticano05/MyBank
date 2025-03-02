package com.pet.bank.exception.type;

import com.pet.bank.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}