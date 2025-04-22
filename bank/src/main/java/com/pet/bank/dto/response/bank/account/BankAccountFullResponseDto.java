package com.pet.bank.dto.response.bank.account;

import com.pet.bank.dto.response.currency.nested.CurrencyDto;
import com.pet.bank.dto.response.loan.nested.LoanShortDto;
import com.pet.bank.dto.response.user.nested.UserShortDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountFullResponseDto {

    private UUID id;

    private UserShortDto owner;

    private Date createdAt;

    private BigDecimal balance;

    private String status;

    private CurrencyDto currency;

    private List<LoanShortDto> loans;

}