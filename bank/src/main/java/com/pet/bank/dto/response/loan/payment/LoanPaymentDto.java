package com.pet.bank.dto.response.loan.payment;

import lombok.*;

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