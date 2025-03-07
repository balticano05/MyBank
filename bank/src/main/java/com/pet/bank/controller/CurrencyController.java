package com.pet.bank.controller;

import com.pet.bank.dto.response.currency.AllCurrenciesResponseDto;
import com.pet.bank.dto.response.exchange.rate.ExchangeRateResponseDto;
import com.pet.bank.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private CurrencyService currencyService;

    @GetMapping
    public AllCurrenciesResponseDto findAllCurrencies() {
        return currencyService.findAllCurrencies();
    }

    @GetMapping("/exchange-rates")
    public ExchangeRateResponseDto getExchangeRate(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency) {
        return currencyService.getExchangeRate(fromCurrency, toCurrency);
    }

}