package com.pet.bank.service;

import com.pet.bank.dto.request.bank.account.BankAccountCreationRequestDto;
import com.pet.bank.dto.request.bank.account.BankAccountUpdateRequestDto;
import com.pet.bank.dto.response.bank.account.AllUserBankAccountsResponseDto;
import com.pet.bank.dto.response.bank.account.BankAccountCreationResponse;
import com.pet.bank.dto.response.bank.account.BankAccountFullResponseDto;
import com.pet.bank.dto.response.bank.account.BankAccountUpdateResponseDto;

import java.util.UUID;

public interface BankAccountService {

    AllUserBankAccountsResponseDto findAllBankAccountsByUserId(UUID userId);

    BankAccountFullResponseDto findBankAccountById(UUID bankAccountId);

    BankAccountCreationResponse createBankAccountForUser(UUID userId, BankAccountCreationRequestDto bankAccountRequest);

    BankAccountUpdateResponseDto updateBankAccountById(UUID bankAccountId, BankAccountUpdateRequestDto bankAccountRequest);

    UUID deleteBankAccountById(UUID bankAccountId);

}