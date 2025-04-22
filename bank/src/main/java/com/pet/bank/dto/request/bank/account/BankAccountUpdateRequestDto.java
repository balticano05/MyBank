package com.pet.bank.dto.request.bank.account;

import com.pet.bank.dto.response.currency.nested.CurrencyDto;
import com.pet.bank.utils.validator.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountUpdateRequestDto {

    @DecimalMin(value = "0.00", message = "Balance must be non-negative")
    private BigDecimal balance;

    @Pattern(regexp = ValidationConstants.BANK_ACCOUNT_STATUS_REGEX,
            message = ValidationConstants.BANK_ACCOUNT_STATUS_MESSAGE)
    private String status;

    @Valid
    private CurrencyDto currency;

}