package com.pet.bank.dto.response.bank.account;

import com.pet.bank.dto.response.currency.nested.CurrencyDto;
import com.pet.bank.dto.response.user.nested.UserShortDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountCreationResponse {

    private UUID id;

    private UserShortDto owner;

    private Date createdAt;

    private BigDecimal balance;

    private String status;

    private CurrencyDto currency;

}