package com.pet.bank.exception.type;

import com.pet.bank.exception.AbstractException;
import lombok.Builder;
import org.springframework.http.HttpStatus;

public class CustomIOException extends AbstractException {

    @Builder
    public CustomIOException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}