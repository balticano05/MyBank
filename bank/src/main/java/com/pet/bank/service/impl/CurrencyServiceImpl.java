package com.pet.bank.service.impl;

import com.pet.bank.dto.mapper.CurrencyMapper;
import com.pet.bank.dto.mapper.ExchangeMapper;
import com.pet.bank.dto.response.currency.AllCurrenciesResponseDto;
import com.pet.bank.dto.response.exchange.rate.ExchangeRateResponseDto;
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

    private final CurrencyRepository currencyRepository;
    private final DataValidationService dataValidationService;
    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    public AllCurrenciesResponseDto findAllCurrencies() {
        return CurrencyMapper.mapCurrenciesToAllCurrenciesResponseDto(currencyRepository.findAll());
    }

    @Override
    public ExchangeRateResponseDto getExchangeRate(String fromCode, String toCode) {

        dataValidationService.existsCurrencyByCode(fromCode, HttpStatus.NOT_FOUND);
        dataValidationService.existsCurrencyByCode(toCode, HttpStatus.NOT_FOUND);
        dataValidationService.existsExchangeRate(fromCode, toCode, HttpStatus.NOT_FOUND);

        if (fromCode.equalsIgnoreCase(toCode)) {
            return ExchangeRateResponseDto.builder()
                    .fromCurrency(fromCode)
                    .toCurrency(toCode)
                    .exchangeRate(BigDecimal.ONE)
                    .dateTime(LocalDateTime.now())
                    .build();
        }

        ExchangeRate directRate = exchangeRateRepository.findByBaseCurrency_CodeAndTargetCurrency_Code(fromCode, toCode);

        return ExchangeMapper.mapEntityToExchangeResponseDto(directRate);
    }

}