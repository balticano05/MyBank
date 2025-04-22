package com.pet.bank.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepayLoanRequestDto {

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

}