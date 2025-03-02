package com.pet.bank.dto.mapper;

import com.pet.bank.dto.response.transaction.AllBankAccountTransactionsResponseDto;
import com.pet.bank.dto.response.transaction.TransactionInfoResponseDto;
import com.pet.bank.dto.response.transaction.TransactionShortDto;
import com.pet.bank.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionMapper {

    public static TransactionInfoResponseDto mapEntityToTransactionInfoResponse(Transaction source){
        return TransactionInfoResponseDto.builder()
                .id(source.getId())
                .fromAccountId(source.getFromAccount().getId())
                .toAccountId(source.getToAccount().getId())
                .amount(source.getAmount())
                .currency(CurrencyMapper.mapEntityToCurrencyDto(source.getCurrency()))
                .transactionType(source.getTransactionType())
                .createdAt(source.getCreatedAt())
                .build();
    }

    public static AllBankAccountTransactionsResponseDto mapEntitiesToAllBankAccountTransactionsResponseDto
            (List<Transaction> transactions){
        return AllBankAccountTransactionsResponseDto.builder()
                .transactions(mapEntitiesToListTransactionShorDto(transactions))
                .build();
    }

    private static List<TransactionShortDto> mapEntitiesToListTransactionShorDto(List<Transaction> source){

        if(source == null)
            return new ArrayList<>();

        return source.stream()
                .map(TransactionMapper::mapEntityToTransactionShortDto)
                .toList();
    }

    private static TransactionShortDto mapEntityToTransactionShortDto(Transaction source){
        return TransactionShortDto.builder()
                .id(source.getId())
                .fromAccountId(source.getFromAccount().getId())
                .toAccountId(source.getToAccount().getId())
                .amount(source.getAmount())
                .transactionType(source.getTransactionType())
                .createdAt(source.getCreatedAt())
                .build();
    }

}
