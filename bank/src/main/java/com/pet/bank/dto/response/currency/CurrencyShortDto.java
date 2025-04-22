package com.pet.bank.dto.response.currency;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyShortDto {

    @NotNull(message = "Currency code is required")
    private String code;

}