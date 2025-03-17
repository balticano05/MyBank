package com.pet.bank.dto.response.currency;

import com.pet.bank.dto.response.currency.nested.CurrencyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllCurrenciesResponseDto {

    private List<CurrencyDto> currencies;

}