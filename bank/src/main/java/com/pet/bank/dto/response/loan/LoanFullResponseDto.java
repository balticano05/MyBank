package com.pet.bank.dto.response.loan;

import com.pet.bank.dto.response.currency.CurrencyDto;
import com.pet.bank.dto.response.currency.CurrencyShortDto;
import com.pet.bank.dto.response.loan.payment.LoanPaymentDto;
import com.pet.bank.dto.response.user.nested.UserShortDto;
import com.pet.bank.entity.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanFullResponseDto {

    private UUID id;

    private BigDecimal amount;

    private UserShortDto user;

    private CurrencyDto currency;

    private BigDecimal interestRate;

    private Date startDate;

    private Date endDate;

    private String status;

    private List<LoanPaymentDto> loanPayments;

}
