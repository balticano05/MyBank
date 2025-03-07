package com.pet.bank.service.impl;

import com.pet.bank.dto.mapper.BankAccountMapper;
import com.pet.bank.dto.request.bank.account.BankAccountCreationRequestDto;
import com.pet.bank.dto.request.bank.account.BankAccountUpdateRequestDto;
import com.pet.bank.dto.response.bank.account.AllUserBankAccountsResponseDto;
import com.pet.bank.dto.response.bank.account.BankAccountCreationResponse;
import com.pet.bank.dto.response.bank.account.BankAccountFullResponseDto;
import com.pet.bank.dto.response.bank.account.BankAccountUpdateResponseDto;
import com.pet.bank.entity.BankAccount;
import com.pet.bank.entity.Currency;
import com.pet.bank.entity.User;
import com.pet.bank.exception.service.DataValidationService;
import com.pet.bank.exception.type.BadRequestException;
import com.pet.bank.repository.BankAccountRepository;
import com.pet.bank.repository.CurrencyRepository;
import com.pet.bank.repository.UserRepository;
import com.pet.bank.service.BankAccountService;
import com.pet.bank.utils.validator.FieldValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final BankAccountRepository bankAccountRepository;
    private final DataValidationService dataValidationService;

    @Override
    public AllUserBankAccountsResponseDto findAllBankAccountsByUserId(UUID userId) {

        List<BankAccount> userBankAccounts = bankAccountRepository.findAllBankAccountsByOwnerId(userId);

        return BankAccountMapper.mapEntitiesToAllUserBankAccountsResponseDto(userBankAccounts);
    }

    @Override
    public BankAccountFullResponseDto findBankAccountById(UUID bankAccountId) {

        BankAccount foundBankAccount = bankAccountRepository.findBankAccountById(bankAccountId);

        return BankAccountMapper.mapEntityToBankAccountFullResponseDto(foundBankAccount);
    }

    @Override
    @Transactional
    public BankAccountCreationResponse createBankAccountForUser(UUID userId, BankAccountCreationRequestDto bankAccountRequest) {

        User foundUser = userRepository.findUserById(userId);

        BankAccount newBankAccount = BankAccountMapper.mapBankAccountCreationRequestDtoToEntity(bankAccountRequest);
        newBankAccount.setCreatedAt(new Date());

        bankAccountRepository.save(newBankAccount);

        return BankAccountMapper.mapEntityToBankAccountCreationResponseDto(newBankAccount);
    }

    @Override
    @Transactional
    public BankAccountUpdateResponseDto updateBankAccountById(UUID bankAccountId, BankAccountUpdateRequestDto bankAccountRequest) {

        dataValidationService.existsUserById(bankAccountRequest.getOwner().getId(), HttpStatus.NOT_FOUND);
        dataValidationService.existsBankAccountById(bankAccountId, HttpStatus.NOT_FOUND);
        dataValidationService.existsCurrencyByCode(bankAccountRequest.getCurrency().getCode(), HttpStatus.NOT_FOUND);

        BankAccount foundBankAccount = bankAccountRepository.findBankAccountById(bankAccountId);

        if (FieldValidator.isNotNull(bankAccountRequest.getBalance())) {

            if (FieldValidator.isNotNegative(bankAccountRequest.getBalance())) {
                throw new BadRequestException("Balance cannot be negative");
            }

            foundBankAccount.setBalance(bankAccountRequest.getBalance());
        }

        if (FieldValidator.isNotEmpty(bankAccountRequest.getStatus())) {

            if (!FieldValidator.isValidBankAccountStatus(bankAccountRequest.getStatus())) {
                throw new BadRequestException("Invalid account status");
            }

            foundBankAccount.setStatus(bankAccountRequest.getStatus());
        }

        if (FieldValidator.isNotNull(bankAccountRequest.getCurrency())) {

            Currency currency = currencyRepository.findCurrencyByCode(bankAccountRequest.getCurrency().getCode());
            foundBankAccount.setCurrency(currency);
        }

        BankAccount updatedAccount = bankAccountRepository.save(foundBankAccount);

        return BankAccountMapper.mapEntityToBankAccountUpdateResponseDto(updatedAccount);
    }

    @Override
    public void deleteBankAccountById(UUID bankAccountId) {
        bankAccountRepository.deleteById(bankAccountId);
    }

}