package com.pet.bank.service.impl;

import com.pet.bank.dto.mapper.CurrencyMapper;
import com.pet.bank.dto.mapper.ExchangeMapper;
import com.pet.bank.dto.response.currency.AllCurrenciesResponseDto;
import com.pet.bank.dto.response.exchange.rate.ExchangeRateResponseDto;
import com.pet.bank.entity.Currency;
import com.pet.bank.entity.ExchangeRate;
import com.pet.bank.exception.service.DataValidationService;
import com.pet.bank.repository.CurrencyRepository;
import com.pet.bank.repository.ExchangeRateRepository;
import com.pet.bank.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;
    private DataValidationService dataValidationService;
    private ExchangeRateRepository exchangeRateRepository;

    @Override
    public AllCurrenciesResponseDto findAllCurrencies() {
        return CurrencyMapper.mapCurrenciesToAllCurrenciesResponseDto(currencyRepository.findAll());
    }

    @Override
    public ExchangeRateResponseDto getExchangeRate(String fromCode, String toCode) {


        dataValidationService.existsCurrencyByCode(fromCode, HttpStatus.NOT_FOUND);
        dataValidationService.existsCurrencyByCode(toCode, HttpStatus.NOT_FOUND);

        Currency base = currencyRepository.findCurrencyByCode(fromCode);
        Currency target = currencyRepository.findCurrencyByCode(toCode);

        dataValidationService.existsExchangeRate(base, target, HttpStatus.NOT_FOUND);

        if (fromCode.equalsIgnoreCase(toCode)) {
            return ExchangeRateResponseDto.builder()
                    .fromCurrency(fromCode)
                    .toCurrency(fromCode)
                    .exchangeRate(BigDecimal.ONE)
                    .dateTime(LocalDateTime.now())
                    .build();
        }

        ExchangeRate directRate = exchangeRateRepository.findByBaseCurrencyAndTargetCurrency(base, target);

        return ExchangeMapper.mapEntityToExchangeResponseDto(directRate);
    }

}