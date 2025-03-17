package com.pet.bank.dto.mapper;

import com.pet.bank.dto.response.currency.AllCurrenciesResponseDto;
import com.pet.bank.dto.response.currency.nested.CurrencyDto;
import com.pet.bank.entity.Currency;

import java.util.List;

public class CurrencyMapper {

    public static AllCurrenciesResponseDto mapCurrenciesToAllCurrenciesResponseDto(List<Currency> source){
        return AllCurrenciesResponseDto.builder()
                .currencies(source.stream()
                        .map(CurrencyMapper::mapEntityToCurrencyDto)
                        .toList())
                .build();
    }

    public static Currency mapCurrencyDtoToEntity(CurrencyDto source){
        return Currency.builder()
                .id(source.getId())
                .code(source.getCode())
                .title(source.getTitle())
                .build();
    }

    public static CurrencyDto mapEntityToCurrencyDto(Currency source){
        return CurrencyDto.builder()
                .id(source.getId())
                .code(source.getCode())
                .title(source.getTitle())
                .build();
    }

}