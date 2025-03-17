package com.pet.bank.service.impl;

import com.pet.bank.dto.mapper.TransactionMapper;
import com.pet.bank.dto.response.transaction.AllBankAccountTransactionsResponseDto;
import com.pet.bank.dto.response.transaction.TransactionInfoResponseDto;
import com.pet.bank.exception.service.DataValidationService;
import com.pet.bank.repository.TransactionRepository;
import com.pet.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final DataValidationService dataValidationService;

    @Override
    public AllBankAccountTransactionsResponseDto findAllTransactionsByBankAccountId(UUID bankAccountId) {

        dataValidationService.existsBankAccountById(bankAccountId, HttpStatus.NOT_FOUND);

        return TransactionMapper.mapEntitiesToAllBankAccountTransactionsResponseDto(transactionRepository
                .findAllTransactionsByFromAccountId(bankAccountId));
    }

    @Override
    public TransactionInfoResponseDto findTransactionById(UUID bankAccountId, UUID transactionId) {

        dataValidationService.existsTransactionWithBankAccount(bankAccountId, transactionId, HttpStatus.NOT_FOUND);

        return TransactionMapper.mapEntityToTransactionInfoResponse(transactionRepository.findTransactionById(transactionId));
    }

}