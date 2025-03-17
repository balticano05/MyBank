package com.pet.bank.dto.response.bank.account;

import com.pet.bank.dto.response.card.nested.CardDto;
import com.pet.bank.dto.response.currency.nested.CurrencyDto;
import com.pet.bank.dto.response.user.nested.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountUpdateResponseDto {

    private UUID id;

    private UserShortDto owner;

    private BigDecimal balance;

    private String status;

    private CurrencyDto currency;

    private List<CardDto> cards;

}