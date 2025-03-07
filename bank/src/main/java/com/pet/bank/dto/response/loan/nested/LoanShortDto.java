package com.pet.bank.dto.response.loan.nested;

import com.pet.bank.entity.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanShortDto {

    private UUID id;

    private BigDecimal amount;

    private Currency currency;

    private BigDecimal interestRate;

    private Date startDate;

    private Date endDate;

    private String status;

}