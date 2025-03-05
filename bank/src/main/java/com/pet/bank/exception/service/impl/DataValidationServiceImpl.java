package com.pet.bank.exception.service.impl;

import com.pet.bank.exception.message.ErrorMessages;
import com.pet.bank.exception.service.DataValidationService;
import com.pet.bank.exception.type.ClientException;
import com.pet.bank.repository.BankAccountRepository;
import com.pet.bank.repository.CardRepository;
import com.pet.bank.repository.CurrencyRepository;
import com.pet.bank.repository.LoanRepository;
import com.pet.bank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.CurrencyEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataValidationServiceImpl implements DataValidationService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final LoanRepository loanRepository;
    private final CurrencyRepository currencyRepository;
    private final BankAccountRepository bankAccountRepository;

    @Override
    public void existsUserById(UUID userId, HttpStatus status) {
        if (!userRepository.existsById(userId)) {
            throw ClientException.builder()
                    .message(ErrorMessages.USER_NOT_FOUND.format(userId))
                    .httpStatus(status)
                    .build();
        }
    }

    @Override
    public void existsBankAccountById(UUID bankAccountId, HttpStatus status) {
        if (!bankAccountRepository.existsById(bankAccountId)) {
            throw ClientException.builder()
                    .message(ErrorMessages.BANK_ACCOUNT_NOT_FOUND.format(bankAccountId))
                    .httpStatus(status)
                    .build();
        }
    }

    @Override
    public void existsCurrencyByCode(String code, HttpStatus status) {
        if(!currencyRepository.existsCurrencyByCode(code)){
            throw ClientException.builder()
                    .message(ErrorMessages.CURRENCY_WITH_CODE_NOT_FOUND.format(code))
                    .httpStatus(status)
                    .build();
        }
    }

    @Override
    public void existsCardById(UUID cardId, HttpStatus status) {
        if(!cardRepository.existsById(cardId)){
            throw ClientException.builder()
                    .message(ErrorMessages.CARD_NOT_FOUND.format(cardId))
                    .httpStatus(status)
                    .build();
        }
    }

    @Override
    public void existsLoanById(UUID loanId, HttpStatus status) {
        if(!loanRepository.existsById(loanId)){
            throw ClientException.builder()
                    .message(ErrorMessages.LOAN_NOT_FOUND.format(loanId))
                    .httpStatus(status)
                    .build();
        }
    }
}
