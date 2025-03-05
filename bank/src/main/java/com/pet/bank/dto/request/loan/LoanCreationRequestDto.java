package com.pet.bank.dto.request.loan;

import com.pet.bank.dto.response.currency.CurrencyShortDto;
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
public class LoanCreationRequestDto {

    private BigDecimal amount;

    private CurrencyShortDto currency;

    private BigDecimal interestRate;

    private Date startDate;

    private Date endDate;

}
