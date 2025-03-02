package com.pet.bank.exception.type;

import com.pet.bank.exception.AbstractException;

import org.springframework.http.HttpStatus;


public class NotFoundException extends AbstractException {

    protected NotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static NotFoundExceptionBuilder builder() {
        return new NotFoundExceptionBuilder();
    }

    public static class NotFoundExceptionBuilder {
        private String message;
        private HttpStatus httpStatus;

        public NotFoundExceptionBuilder message(String message) {
            this.message = message;
            return this;
        }

        public NotFoundExceptionBuilder httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public NotFoundException build() {
            return new NotFoundException(message, httpStatus);
        }
    }
}