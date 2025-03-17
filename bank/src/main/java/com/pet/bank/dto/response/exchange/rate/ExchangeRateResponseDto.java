package com.pet.bank.dto.response.exchange.rate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponseDto {

    private String fromCurrency;

    private String toCurrency;

    private BigDecimal exchangeRate;

    private LocalDateTime dateTime;

}