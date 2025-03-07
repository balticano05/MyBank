package com.pet.bank.service;

import com.pet.bank.dto.response.transaction.AllBankAccountTransactionsResponseDto;
import com.pet.bank.dto.response.transaction.TransactionInfoResponseDto;

import java.util.UUID;

public interface TransactionService {

    AllBankAccountTransactionsResponseDto findAllTransactionsByBankAccountId(UUID bankAccountId);

    TransactionInfoResponseDto findTransactionById(UUID transactionId);

}