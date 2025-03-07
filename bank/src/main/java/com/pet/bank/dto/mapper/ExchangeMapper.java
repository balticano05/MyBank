package com.pet.bank.dto.mapper;

import com.pet.bank.dto.response.exchange.rate.ExchangeRateResponseDto;
import com.pet.bank.entity.ExchangeRate;

public class ExchangeMapper {

    public static ExchangeRateResponseDto mapEntityToExchangeResponseDto(ExchangeRate source) {
        return ExchangeRateResponseDto.builder()
                .fromCurrency(source.getBaseCurrency().getCode())
                .toCurrency(source.getBaseCurrency().getCode())
                .exchangeRate(source.getRate())
                .dateTime(source.getActualDate())
                .build();
    }

}