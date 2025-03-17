package com.pet.bank.exception.type;

import com.pet.bank.exception.AbstractException;
import lombok.Builder;
import org.springframework.http.HttpStatus;

public class ClientException extends AbstractException {

    @Builder
    protected ClientException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
