package com.pet.bank.exception.type;

import com.pet.bank.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class SecurityConfigurationException extends AbstractException {

    public SecurityConfigurationException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}