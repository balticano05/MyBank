package com.pet.bank.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;
    private final Instant timestamp = Instant.now();

}
