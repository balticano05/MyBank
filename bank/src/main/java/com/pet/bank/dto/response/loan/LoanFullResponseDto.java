package com.pet.bank.dto.response.loan;

import com.pet.bank.dto.response.currency.nested.CurrencyDto;
import com.pet.bank.dto.response.loan.payment.LoanPaymentDto;
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