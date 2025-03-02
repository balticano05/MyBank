package com.pet.bank.dto.request.bank.account;

import com.pet.bank.dto.response.currency.CurrencyDto;
import com.pet.bank.entity.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

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
