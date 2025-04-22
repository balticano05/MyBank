package com.pet.bank.exception.service;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public interface DataValidationService {

    void existsUserById(UUID userId, HttpStatus status);

    void existsCredentialByLogin(String login, HttpStatus status);

    void existsBankAccountById(UUID bankAccountId, HttpStatus status);

    void existsCurrencyByCode(String code, HttpStatus status);

    void existsLoanById(UUID loanId, HttpStatus status);

    void existsExchangeRate(String fromCurrencyCode, String toCurrencyCode, HttpStatus status);

    void existsCredentialById(UUID credentialId, HttpStatus status);

    void existsUserByCredentialId(UUID credentialId, HttpStatus status);

    void existsTransactionWithBankAccount(UUID bankAccountId, UUID transactionId, HttpStatus status);

}