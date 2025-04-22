package com.pet.bank.dto.response.exchange.rate;

import lombok.*;

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