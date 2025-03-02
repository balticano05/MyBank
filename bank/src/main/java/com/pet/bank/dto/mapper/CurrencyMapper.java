package com.pet.bank.dto.mapper;

import com.pet.bank.dto.response.currency.CurrencyDto;
import com.pet.bank.entity.Currency;

public class CurrencyMapper {

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
