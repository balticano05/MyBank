package com.pet.bank.controller;

import com.pet.bank.dto.response.transaction.AllBankAccountTransactionsResponseDto;
import com.pet.bank.dto.response.transaction.TransactionInfoResponseDto;
import com.pet.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/accounts/{bankAccountId}/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public AllBankAccountTransactionsResponseDto findAllTransactionsByBankAccountId(@PathVariable UUID bankAccountId) {
        return transactionService.findAllTransactionsByBankAccountId(bankAccountId);
    }

    @GetMapping("/{transactionId}")
    public TransactionInfoResponseDto findTransactionById(
            @PathVariable UUID bankAccountId,
            @PathVariable UUID transactionId) {
        return transactionService.findTransactionById(bankAccountId, transactionId);
    }

}