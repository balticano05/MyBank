package com.pet.bank.dto.response.loan.nested;

import com.pet.bank.entity.Currency;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanShortDto extends RepresentationModel<LoanShortDto> {

    private UUID id;

    private BigDecimal amount;

    private Currency currency;

    private BigDecimal interestRate;

    private Date startDate;

    private Date endDate;

    private String status;

}