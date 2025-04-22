package com.pet.bank.dto.response.currency;

import com.pet.bank.dto.response.currency.nested.CurrencyDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllCurrenciesResponseDto {

    private List<CurrencyDto> currencies;

}