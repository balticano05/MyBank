package com.pet.bank.exception.type;

import com.pet.bank.exception.AbstractException;
import lombok.Builder;
import org.springframework.http.HttpStatus;

public class AuthenticationFailedException extends AbstractException {

    @Builder
    public AuthenticationFailedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

}