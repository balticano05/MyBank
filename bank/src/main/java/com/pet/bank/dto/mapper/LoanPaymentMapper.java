package com.pet.bank.dto.mapper;

import com.pet.bank.dto.response.loan.payment.LoanPaymentDto;
import com.pet.bank.entity.LoanPayment;

import java.util.ArrayList;
import java.util.List;

public class LoanPaymentMapper {

    public static List<LoanPaymentDto> mapEntitiesToListLoanPaymentDto(List<LoanPayment> source){

        if(source == null)
            return new ArrayList<>();

        return source.stream()
                .map(LoanPaymentMapper::mapEntityToLoanPaymentDto)
                .toList();
    }

    public static LoanPaymentDto mapEntityToLoanPaymentDto(LoanPayment source){
        return LoanPaymentDto.builder()
                .id(source.getId())
                .paymentAmount(source.getPaymentAmount())
                .paymentDate(source.getPaymentDate())
                .status(source.getStatus())
                .build();
    }

}
