package com.pet.bank.exception.type;

import com.pet.bank.exception.AbstractException;
import lombok.Builder;
import org.springframework.http.HttpStatus;

public class CustomServletException extends AbstractException {

    @Builder
    public CustomServletException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}