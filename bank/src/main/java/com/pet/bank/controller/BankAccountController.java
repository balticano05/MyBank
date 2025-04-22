package com.pet.bank.controller;

import com.pet.bank.dto.request.bank.account.BankAccountCreationRequestDto;
import com.pet.bank.dto.request.bank.account.BankAccountUpdateRequestDto;
import com.pet.bank.dto.response.bank.account.AllUserBankAccountsResponseDto;
import com.pet.bank.dto.response.bank.account.BankAccountCreationResponse;
import com.pet.bank.dto.response.bank.account.BankAccountFullResponseDto;
import com.pet.bank.dto.response.bank.account.BankAccountUpdateResponseDto;
import com.pet.bank.service.BankAccountService;
import com.pet.bank.service.LinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final LinkService linkService;
    private final BankAccountService bankAccountService;

    @GetMapping
    public AllUserBankAccountsResponseDto findAllBankAccounts(@PathVariable UUID userId) {
        AllUserBankAccountsResponseDto allUserBankAccountsResponseDto = bankAccountService.findAllBankAccountsByUserId(userId);
        return linkService.addLinksToAllUserBankAccountsResponseDto(allUserBankAccountsResponseDto, userId);
    }

    @GetMapping("/{bankAccountId}")
    public BankAccountFullResponseDto findBankAccountById(@Valid @PathVariable UUID bankAccountId) {
        return bankAccountService.findBankAccountById(bankAccountId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccountCreationResponse createBankAccountForUser(
            @PathVariable UUID userId,
            @Valid @RequestBody BankAccountCreationRequestDto bankAccountRequest) {
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UUID deleteBankAccountById(@PathVariable UUID bankAccountId) {
        return bankAccountService.deleteBankAccountById(bankAccountId);
    }

}