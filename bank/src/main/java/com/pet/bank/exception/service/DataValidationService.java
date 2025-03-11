package com.pet.bank.exception.service;

import com.pet.bank.entity.Currency;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public interface DataValidationService {

    void existsUserById(UUID userId, HttpStatus status);

    void existsBankAccountById(UUID bankAccountId, HttpStatus status);

    void existsCurrencyByCode(String code, HttpStatus status);

    void existsCardById(UUID cardId, HttpStatus status);

    void existsLoanById(UUID loanId, HttpStatus status);

    void existsExchangeRate(Currency fromCurrency, Currency toCurrency,  HttpStatus status);

    void existsCredentialById(UUID credentialId, HttpStatus status);

    void existsUserByCredentialId(UUID credentialId, HttpStatus status);

}
