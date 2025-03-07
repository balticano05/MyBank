package com.pet.bank.dto.request.bank.account;

import com.pet.bank.dto.response.currency.nested.CurrencyDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountCreationRequestDto {

    private BigDecimal balance;

    private String status;

    private CurrencyDto currency;

}