package com.pet.bank.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    USER_NOT_FOUND("User with id %s not found"),
    BANK_ACCOUNT_NOT_FOUND("Bank account with id %s not found"),
    CURRENCY_WITH_CODE_NOT_FOUND("Currency with code %s not found"),
    LOAN_NOT_FOUND("Loan with id %s not found"),
    CARD_NOT_FOUND("Currency with id %s not found"),
    INVALID_REQUEST_DATA("Invalid request parameters: %s"),
    EXCHANGE_RATE_FOR_CURRENCIES_NOT_FOUND("Exchange rate for currencies %s and %s"),
    CREDENTIAL_NOT_FOUND("Credential with id %s not found"),
    THERE_IS_NO_TRANSACTION_FOR_SUCH_BANK_ACCOUNT("There is no transaction for such account"),
    THERE_IS_ALREADY_A_USER_WITH_SUCH_CREDENTIALS("There is already a user with such credentials"),
    USER_WITH_LOGIN_NOT_FOUND("User with login not found: %s");

    private final String message;

    public String format(Object... args) {
        return String.format(message, args);
    }

}