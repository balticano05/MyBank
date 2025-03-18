package com.pet.bank.exception.type;

import com.pet.bank.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class MissingTokenException extends AbstractException {

    public MissingTokenException() {
        super("Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
    }

}