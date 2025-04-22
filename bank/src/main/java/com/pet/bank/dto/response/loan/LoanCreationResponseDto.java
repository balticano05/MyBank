package com.pet.bank.dto.response.loan;

import com.pet.bank.dto.response.currency.CurrencyShortDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanCreationResponseDto {

    private UUID id;

    private BigDecimal amount;

    private CurrencyShortDto currency;

    private BigDecimal interestRate;

    private Date startDate;

    private Date endDate;

    private String status;

}