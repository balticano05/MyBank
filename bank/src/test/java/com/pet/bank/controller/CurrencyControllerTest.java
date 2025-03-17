package com.pet.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.bank.entity.Currency;
import com.pet.bank.entity.ExchangeRate;
import com.pet.bank.repository.CurrencyRepository;
import com.pet.bank.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Transactional
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    private Currency createCurrency(String code, String name) {
        return Currency.builder()
                .code(code)
                .title(name)
                .build();
    }

    private ExchangeRate createExchangeRate(Currency from, Currency to, BigDecimal rate) {
        return ExchangeRate.builder()
                .baseCurrency(from)
                .targetCurrency(to)
                .actualDate(LocalDateTime.now())
                .rate(rate)
                .build();
    }

    @Test
    @SneakyThrows
    public void findAllCurrenciesTest() {

        currencyRepository.saveAll(List.of(
                createCurrency("USD", "US Dollar"),
                createCurrency("EUR", "Euro")
        ));

        mockMvc.perform(get("/api/v1/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currencies", hasSize(2)))
                .andExpect(jsonPath("$.currencies[0].code").value("USD"))
                .andExpect(jsonPath("$.currencies[1].code").value("EUR"));
    }

    @Test
    @SneakyThrows
    public void getExchangeRateTest() {

        Currency usd = currencyRepository.save(createCurrency("USD", "US Dollar"));
        Currency eur = currencyRepository.save(createCurrency("EUR", "Euro"));

        exchangeRateRepository.save(createExchangeRate(usd, eur, new BigDecimal("0.92")));

        System.out.println(exchangeRateRepository.findByBaseCurrency_CodeAndTargetCurrency_Code(usd.getCode(), eur.getCode()));

        mockMvc.perform(get("/api/v1/currencies/exchange-rates")
                        .param("fromCurrency", "USD")
                        .param("toCurrency", "EUR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exchangeRate").value(0.92));
    }

    @Test
    @SneakyThrows
    public void getExchangeRateWhenCurrencyNotFoundTest() {

        mockMvc.perform(get("/api/v1/currencies/exchange-rates")
                        .param("fromCurrency", "USD")
                        .param("toCurrency", "EUR"))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void getExchangeRateInvalidRequestTest() {

        mockMvc.perform(get("/api/v1/currencies/exchange-rates"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void getExchangeRateWithSameCurrenciesTest() {

        Currency usd = currencyRepository.save(createCurrency("USD", "US Dollar"));
        Currency eur = currencyRepository.save(createCurrency("EUR", "Euro"));

        exchangeRateRepository.save(createExchangeRate(usd, usd, new BigDecimal("1.0")));

        mockMvc.perform(get("/api/v1/currencies/exchange-rates")
                        .param("fromCurrency", "USD")
                        .param("toCurrency", "USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exchangeRate").value(1.0));
    }

    @Test
    @SneakyThrows
    public void getExchangeRateReverseRateTest() {

        Currency usd = currencyRepository.save(createCurrency("USD", "US Dollar"));
        Currency eur = currencyRepository.save(createCurrency("EUR", "Euro"));

        exchangeRateRepository.save(createExchangeRate(usd, eur, new BigDecimal("0.92")));
        exchangeRateRepository.save(createExchangeRate(eur, usd, new BigDecimal("1.09")));

        mockMvc.perform(get("/api/v1/currencies/exchange-rates")
                        .param("fromCurrency", "EUR")
                        .param("toCurrency", "USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exchangeRate").value(1.09));
    }

}
