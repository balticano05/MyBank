package com.pet.bank.dto.response.loan.payment;

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
public class LoanPaymentDto {

    private UUID id;

    private BigDecimal paymentAmount;

    private Date paymentDate;

    private String status;

}