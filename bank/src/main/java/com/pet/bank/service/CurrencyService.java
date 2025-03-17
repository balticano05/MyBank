package com.pet.bank.service;

import com.pet.bank.dto.response.currency.AllCurrenciesResponseDto;
import com.pet.bank.dto.response.exchange.rate.ExchangeRateResponseDto;

public interface CurrencyService {

    AllCurrenciesResponseDto findAllCurrencies();

    ExchangeRateResponseDto getExchangeRate(String fromCode, String toCode);

}