package com.pet.bank.exception.service;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public interface DataValidationService {

    void existsUserById(UUID userId, HttpStatus status);

    void existsBankAccountById(UUID bankAccountId, HttpStatus status);

    void existsCurrencyByCode(String code, HttpStatus status);

    void existsCardById(UUID cardId, HttpStatus status);

    void existsLoanById(UUID loanId, HttpStatus status);

}
