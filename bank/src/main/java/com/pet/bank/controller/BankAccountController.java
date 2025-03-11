package com.pet.bank.controller;

import com.pet.bank.dto.request.bank.account.BankAccountCreationRequestDto;
import com.pet.bank.dto.request.bank.account.BankAccountUpdateRequestDto;
import com.pet.bank.dto.response.bank.account.AllUserBankAccountsResponseDto;
import com.pet.bank.dto.response.bank.account.BankAccountCreationResponse;
import com.pet.bank.dto.response.bank.account.BankAccountFullResponseDto;
import com.pet.bank.dto.response.bank.account.BankAccountUpdateResponseDto;
import com.pet.bank.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping
    public AllUserBankAccountsResponseDto findAllBankAccounts(@PathVariable UUID userId) {
        return bankAccountService.findAllBankAccountsByUserId(userId);
    }

    @GetMapping("/{bankAccountId}")
    public BankAccountFullResponseDto findBankAccountById(@PathVariable UUID bankAccountId) {
        return bankAccountService.findBankAccountById(bankAccountId);
    }

    @PostMapping
    public BankAccountCreationResponse createBankAccountForUser(
            @PathVariable UUID userId,
            @RequestBody BankAccountCreationRequestDto bankAccountRequest) {
        return bankAccountService.createBankAccountForUser(userId, bankAccountRequest);
    }

    @PutMapping("/{bankAccountId}")
    public BankAccountUpdateResponseDto updateBankAccountById(
            @PathVariable UUID bankAccountId,
            @RequestBody BankAccountUpdateRequestDto bankAccountUpdateRequestDto
    ) {
        return bankAccountService.updateBankAccountById(bankAccountId, bankAccountUpdateRequestDto);
    }

    @DeleteMapping("/{bankAccountId}")
    public void deleteBankAccountById(@PathVariable UUID bankAccountId) {
        bankAccountService.deleteBankAccountById(bankAccountId);
    }

}