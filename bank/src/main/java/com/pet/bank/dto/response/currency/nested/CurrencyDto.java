package com.pet.bank.dto.response.currency.nested;

import com.pet.bank.utils.validator.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {

    private UUID id;

    @NotBlank(message = "Currency code is required")
    @Size(min = ValidationConstants.CURRENCY_CODE_LENGTH,
            max = ValidationConstants.CURRENCY_CODE_LENGTH,
            message = ValidationConstants.CURRENCY_CODE_MESSAGE)
    private String code;

    @NotBlank(message = "Currency title is required")
    private String title;

}