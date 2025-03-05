package com.pet.bank.dto.mapper;

import com.pet.bank.dto.response.currency.CurrencyShortDto;
import com.pet.bank.dto.response.loan.AllUserLoansResponseDto;
import com.pet.bank.dto.response.loan.LoanCreationResponseDto;
import com.pet.bank.dto.response.loan.LoanDto;
import com.pet.bank.dto.response.loan.LoanFullResponseDto;
import com.pet.bank.dto.response.loan.LoanShortDto;
import com.pet.bank.entity.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanMapper {

    public static LoanCreationResponseDto mapEntityToLoanCreationResponseDto(Loan source){
        return LoanCreationResponseDto.builder()
                .id(source.getId())
                .amount(source.getAmount())
                .currency(CurrencyShortDto.builder()
                        .code(source.getCurrency().getCode())
                        .build())
                .interestRate(source.getInterestRate())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .status(source.getStatus())
                .build();
    }

    public static LoanFullResponseDto mapEntityToFullResponseDto(Loan source){
        return LoanFullResponseDto.builder()
                .id(source.getId())
                .amount(source.getAmount())
                .user(UserMapper.mapEntityToUserShortDto(source.getUser()))
                .currency(CurrencyMapper.mapEntityToCurrencyDto(source.getCurrency()))
                .interestRate(source.getInterestRate())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .status(source.getStatus())
                .loanPayments(LoanPaymentMapper.mapEntitiesToListLoanPaymentDto(source.getLoanPayments()))
                .build();
    }

    public static AllUserLoansResponseDto mapEntitiesToAllUserLoansResponseDto(List<Loan> source){
        return AllUserLoansResponseDto.builder()
                .loans(mapEntitiesToListLoanShortDto(source))
                .build();
    }

    public static List<LoanShortDto> mapEntitiesToListLoanShortDto(List<Loan> source){

        if(source == null)
            return new ArrayList<>();

        return source.stream()
                .map(LoanMapper::mapEntityToLoanShortDto)
                .toList();
    }

    public static List<LoanDto> mapEntitiesToListLoanDto(List<Loan> source){

        if(source == null)
            return new ArrayList<>();

        return source.stream()
                .map(LoanMapper::mapEntityToLoanDto)
                .toList();
    }

    public static LoanDto mapEntityToLoanDto(Loan source){
        return LoanDto.builder()
                .id(source.getId())
                .amount(source.getAmount())
                .currency(source.getCurrency())
                .interestRate(source.getInterestRate())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .status(source.getStatus())
                .loanPayments(LoanPaymentMapper.mapEntitiesToListLoanPaymentDto(source.getLoanPayments()))
                .build();
    }

    private static LoanShortDto mapEntityToLoanShortDto(Loan source){
        return LoanShortDto.builder()
                .id(source.getId())
                .amount(source.getAmount())
                .currency(source.getCurrency())
                .interestRate(source.getInterestRate())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .status(source.getStatus())
                .build();
    }

}
