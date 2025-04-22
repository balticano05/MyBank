package com.pet.bank.dto.mapper;

import com.pet.bank.dto.request.bank.account.BankAccountCreationRequestDto;
import com.pet.bank.dto.response.bank.account.AllUserBankAccountsResponseDto;
import com.pet.bank.dto.response.bank.account.BankAccountCreationResponse;
import com.pet.bank.dto.response.bank.account.BankAccountFullResponseDto;
import com.pet.bank.dto.response.bank.account.BankAccountUpdateResponseDto;
import com.pet.bank.dto.response.bank.account.nested.BankAccountDto;
import com.pet.bank.entity.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class BankAccountMapper {

    public static BankAccountDto mapEntityToBankAccountDto(BankAccount source) {
        return BankAccountDto.builder()
                .id(source.getId())
                .createdAt(source.getCreatedAt())
                .balance(source.getBalance())
                .status(source.getStatus())
                .currency(source.getCurrency())
                .build();
    }

    public static BankAccountCreationResponse mapEntityToBankAccountCreationResponseDto(BankAccount source) {
        return BankAccountCreationResponse.builder()
                .id(source.getId())
                .owner(UserMapper.mapEntityToUserShortDto(source.getOwner()))
                .createdAt(source.getCreatedAt())
                .balance(source.getBalance())
                .status(source.getStatus())
                .currency(CurrencyMapper.mapEntityToCurrencyDto(source.getCurrency()))
                .build();
    }

    public static BankAccountUpdateResponseDto mapEntityToBankAccountUpdateResponseDto(BankAccount source) {
        return BankAccountUpdateResponseDto.builder()
                .id(source.getId())
                .owner(UserMapper.mapEntityToUserShortDto(source.getOwner()))
                .balance(source.getBalance())
                .status(source.getStatus())
                .currency(CurrencyMapper.mapEntityToCurrencyDto(source.getCurrency()))
                .build();
    }

    public static BankAccountFullResponseDto mapEntityToBankAccountFullResponseDto(BankAccount source) {
        return BankAccountFullResponseDto.builder()
                .id(source.getId())
                .owner(UserMapper.mapEntityToUserShortDto(source.getOwner()))
                .createdAt(source.getCreatedAt())
                .balance(source.getBalance())
                .status(source.getStatus())
                .currency(CurrencyMapper.mapEntityToCurrencyDto(source.getCurrency()))
                .loans(LoanMapper.mapEntitiesToListLoanShortDto(source.getLoans()))
                .build();
    }

    public static BankAccount mapBankAccountCreationRequestDtoToEntity(BankAccountCreationRequestDto source) {
        return BankAccount.builder()
                .balance(source.getBalance())
                .status(source.getStatus())
                .build();
    }

    public static AllUserBankAccountsResponseDto mapEntitiesToAllUserBankAccountsResponseDto(List<BankAccount> source) {
        return AllUserBankAccountsResponseDto.builder()
                .bankAccounts(mapEntitiesToListBankAccountDto(source))
                .build();
    }


    public static List<BankAccountDto> mapEntitiesToListBankAccountDto(List<BankAccount> source) {

        if (source == null)
            return new ArrayList<>();

        return source.stream()
                .map(BankAccountMapper::mapEntityToBankAccountDto)
                .toList();
    }

}